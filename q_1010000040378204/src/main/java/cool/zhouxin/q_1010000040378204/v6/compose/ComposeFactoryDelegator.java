package cool.zhouxin.q_1010000040378204.v6.compose;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Compose Bean实例化的委托者，该类返回的Compose Bean里还没有初始化填充属性，
 * 属性填充在ComposeFactory中进行
 * @author zhouxin
 * @since 2021/7/26 8:56
 */
@Component
class ComposeFactoryDelegator implements BeanFactoryAware, SmartInitializingSingleton {

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

    /**
     * 根据接口实例化Compose
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T inst(String cacheKey, Class<T> tClass) {
        T inst = (T) this.composeCache.computeIfAbsent(cacheKey, k -> this.createCompose(tClass));
        return inst;
    }

    public <T> List<T> getBeans(Class<T> tClass) {
        List<T> beans = this.targetBeanCache.get(tClass);
        return beans;
    }

    public Enum getEnum(Object bean) {
        Enum anEnum = this.targetBeanEnumCache.get(AopUtils.getTargetClass(bean));
        return anEnum;
    }

    private Object createCompose(Class tClass) {
        String beanName = ComposeFactoryBeanRegistrar.getBeanName(tClass);
        Object compose = this.beanFactory.getBean(beanName, tClass);
        return compose;
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

    private List findTargetBeans(Class tClass) {
        Collection<?> beans = this.beanFactory.getBeansOfType(tClass, false, true).values();
        return beans.stream().collect(Collectors.toList());
    }
}
