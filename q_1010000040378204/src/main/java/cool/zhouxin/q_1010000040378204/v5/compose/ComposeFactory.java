package cool.zhouxin.q_1010000040378204.v5.compose;

import cool.zhouxin.q_1010000040378204.utils.AopTargetUtils;
import cool.zhouxin.q_1010000040378204.utils.ExceptionEnv;
import cool.zhouxin.q_1010000040378204.utils.GenericArrayUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Compose的工厂，用于根据接口类型生成对应的Compose Bean
 * @author zhouxin
 * @since 2021/7/25 17:10
 */
@Component
@Validated
public class ComposeFactory {

    @Autowired
    private ComposeFactoryDelegator delegator;

    /**
     * 根据接口类型和实现类型数组返回对应的Compose Bean
     * @param tClass
     * @param types 若为空，则默认返回所有的type相关的实现类的Compose
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R extends Enum> T create(Class<T> tClass, R... types) {
        // types的空处理，若为空，默认取所有的类型，而所有的类型根据tClass上注解的类型来决定
        R[] finalTypes = this.emptyProcess(tClass, types);

        // 根据目标接口tClass来创建其默认的Compose Bean对象
        String cacheKey = this.buildComposeCacheKey(tClass, finalTypes);
        T compose = this.delegator.inst(cacheKey, tClass);

        // 获取目标接口tClass的所有的单例Bean
        List<T> allTargetBeans = this.delegator.getBeans(tClass);
        // 根据类型finalTypes去过滤需要的Beans
        List<T> targetBeans = this.filterBeans(allTargetBeans, finalTypes);

        // 把符合要求的Beans注入到Compose中，完成最后属性设置
        Object originalCompose = ExceptionEnv.handler(() -> AopTargetUtils.getTarget(compose));
        AbstractCompose abstractCompose = AbstractCompose.class.cast(originalCompose);
        abstractCompose.add(targetBeans);

        return compose;
    }

    /**
     * 根据接口类型和排除的实现类型数组返回对应的Compose Bean
     * @param tClass
     * @param excludeTypes 不能为空
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R extends Enum> T createByExclude(Class<T> tClass,
                @Size(min = 1, message = "exclude types must be at least one") R... excludeTypes) {
        R[] finalTypes = this.excludeProcess(tClass, excludeTypes);
        return this.create(tClass, finalTypes);
    }



    private <T> List<T> filterBeans(List<T> allTargetBeans, Enum[] finalTypes) {
        Set<Enum> finalTypeSet = Stream.of(finalTypes).collect(Collectors.toSet());
        List<T> finalBeans = allTargetBeans.stream()
                                           .filter(bean ->
                                                finalTypeSet.contains(this.delegator.getEnum(bean)))
                                           .collect(Collectors.toList());
        return finalBeans;
    }

    private <T, R extends Enum> R[] excludeProcess(Class<T> tClass, R[] excludeTypes) {
        R[] allEnums = this.findAllEnum(tClass);
        Set<Enum> excludeEnumSet = Stream.of(excludeTypes)
                                         .collect(Collectors.toSet());

        R[] finalEnums = Stream.of(allEnums)
                               .filter(this.negate(excludeEnumSet::contains))
                               .toArray(GenericArrayUtils.genericArray(allEnums));
        return finalEnums;
    }

    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }

    private <T, R extends Enum> R[] emptyProcess(Class<T> tClass, R[] types) {
        if (types != null && types.length > 0) return types;
        R[] allEnums = this.findAllEnum(tClass);
        return allEnums;
    }

    private <T extends Enum> T[] findAllEnum(Class tClass) {
        ComposeType composeType = AnnotationUtils.findAnnotation(tClass, ComposeType.class);
        if (composeType == null) throw new RuntimeException("@ComposeType not found in: " + tClass.getName());

        Class<T> targetEnumClass = (Class<T>) composeType.value();
        T[] enumConstants = targetEnumClass.getEnumConstants();
        return enumConstants;
    }

    private <T, R extends Enum> String buildComposeCacheKey(Class<T> tClass, R[] types) {
        return Stream.concat(Stream.of(tClass.getName()), Stream.of(types).map(R::name))
                .collect(Collectors.joining("-"));
    }
}
