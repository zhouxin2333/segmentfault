package cool.zhouxin.q_1010000040378204.v5.compose;

import cool.zhouxin.q_1010000040378204.utils.AopTargetUtils;
import cool.zhouxin.q_1010000040378204.utils.ExceptionEnv;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Compose的工厂，用于根据接口类型生成对应的Compose Bean
 * @author zhouxin
 * @since 2021/7/25 17:10
 */
@Component
public class ComposeFactory implements BeanFactoryAware, SmartInitializingSingleton {

    private ListableBeanFactory beanFactory;
    // 用于缓存生成的Compose Bean，其key为目标接口Class的名字和过滤的types
    private Map<String, Object> composeCache = new HashMap<>();
    // 一个目标接口下有多少单例Bean（排除ComposeFactoryBean生成那个多例bean）
    private Map<Class, List> targetBeanCache = new HashMap<>();
    // 目标接口下所有单例Bean上的被ComposeTypeMeta注解的注解的value值缓存
    private Map<Class, Enum> targetBeanEnumCache = new HashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = ListableBeanFactory.class.cast(beanFactory);
    }

    @Override
    public void afterSingletonsInstantiated() {
        // 获取当前所有需要生成Compose的接口Class集合
        Set<Class> allTargetClass = ComposeFactoryBeanRegistrar.getAllTargetClass();

        // 根据接口Class集合，可以初始化缓存

        allTargetClass.stream()
                // 1. 初始化接口对应的所有单例Bean的缓存
                .map(tClass -> this.targetBeanCache.computeIfAbsent(tClass, this::findTargetBeans))
                .flatMap(List::stream)
                // 若开启@Async，此时的Bean就是代理对象了，要获取真实的Class才行
                .map(AopUtils::getTargetClass)
                // 2. 初始化所有单例Bean上标注的ComposeTypeMeta注解的注解的枚举值
                .forEach(beanClass -> this.targetBeanEnumCache.computeIfAbsent(beanClass, this::findTargetBeanEnum));

    }

    public <T, R extends Enum> T get(Class<T> tClass, R... types) {
        // types的空处理，若为空，默认取所有的类型，而所有的类型根据tClass上注解的类型来决定
        R[] finalTypes = this.emptyProcess(tClass, types);

        // 根据目标接口tClass来创建其默认的Compose Bean对象
        String key = this.buildComposeCacheKey(tClass, finalTypes);
        T compose = (T) this.composeCache.computeIfAbsent(key, k -> this.createCompose(tClass));

        // 获取目标接口tClass的所有的单例Bean
        List allTargetBeans = this.targetBeanCache.get(tClass);
        // 根据类型finalTypes去过滤需要的Beans
        List targetBeans = this.filterBeans(allTargetBeans, finalTypes);

        // 把符合要求的Beans注入到Compose中，完成最后属性设置
        Object originalCompose = ExceptionEnv.handler(() -> AopTargetUtils.getTarget(compose));
        AbstractCompose abstractCompose = AbstractCompose.class.cast(originalCompose);
        abstractCompose.add(targetBeans);

        return compose;
    }

    private List filterBeans(List<?> allTargetBeans, Enum[] finalTypes) {
        Set<Enum> finalTypeSet = Stream.of(finalTypes).collect(Collectors.toSet());
        List finalBeans = allTargetBeans.stream()
                                        .filter(bean ->
                                                finalTypeSet.contains(
                                                        this.targetBeanEnumCache.get(AopUtils.getTargetClass(bean))))
                                        .collect(Collectors.toList());
        return finalBeans;
    }

    private Enum findTargetBeanEnum(Class tClass) {
        Annotation[] annotations = tClass.getAnnotations();

        for (Annotation annotation : annotations) {
            AnnotatedElement annotatedElement = AnnotatedElementUtils.forAnnotations(annotation);
            // 该注解是否被ComposeTypeMeta标注
            boolean annotated = AnnotatedElementUtils.isAnnotated(annotatedElement, ComposeTypeMeta.class);
            // 没有被ComposeTypeMeta标注的注解就continue
            if (!annotated) continue;

            AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(
                    annotatedElement, annotation.annotationType().getName());
            AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(mergedAnnotationAttributes);
            Enum anEnum = annotationAttributes.getEnum(ComposeTypeMeta.METHOD_NAME);
            return anEnum;
        }
        throw new RuntimeException("can't find annotation which annotated by @ComposeTypeMeta");
    }

    private <T, R extends Enum> R[] emptyProcess(Class<T> tClass, R[] types) {
        if (types != null && types.length > 0) return types;

        ComposeType composeType = AnnotationUtils.findAnnotation(tClass, ComposeType.class);
        if (composeType == null) throw new RuntimeException("@ComposeType not found in: " + tClass.getName());

        Class<R> targetEnumClass = (Class<R>) composeType.value();
        R[] enumConstants = targetEnumClass.getEnumConstants();
        return enumConstants;
    }

    private List findTargetBeans(Class tClass) {
        Collection<?> beans = this.beanFactory.getBeansOfType(tClass, false, true).values();
        return beans.stream().collect(Collectors.toList());
    }

    private Object createCompose(Class tClass) {
        String beanName = ComposeFactoryBeanRegistrar.getBeanName(tClass);
        Object compose = this.beanFactory.getBean(beanName, tClass);
        return compose;
    }

    private <T, R extends Enum> String buildComposeCacheKey(Class<T> tClass, R[] types) {
        return Stream.concat(Stream.of(tClass.getName()), Stream.of(types).map(R::name))
                     .collect(Collectors.joining("-"));
    }
}
