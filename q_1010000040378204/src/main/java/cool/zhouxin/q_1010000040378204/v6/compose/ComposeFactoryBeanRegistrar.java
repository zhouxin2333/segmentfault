package cool.zhouxin.q_1010000040378204.v6.compose;

import cool.zhouxin.q_1010000040378204.utils.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 根据当前需要生成Compose的接口来创建帮助生成Compose的ComposeFactoryBean
 * @author zhouxin
 * @since 2021/7/25 16:20
 */
class ComposeFactoryBeanRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Map<Class, String> beanNameCache = new HashMap<>();
    private static final String DEFAULT_COMPOSE_METHOD_ADVICE_BEAN_NAME =
            StringUtils.toFirstLowerCase(DefaultComposeMethodAdvice.class.getSimpleName());
    private static final Class DEFAULT_FACTORY_BEAN_CLASS = ComposeProxyFactoryBean.class;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry registry) {
        // 获取当前EnableCompose注解的信息
        Map<String, Object> map = annotationMetadata.getAnnotationAttributes(EnableCompose.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(map);

        // 获取当前有哪些接口要做Compose生成
        Class<?>[] composeClassArray = annotationAttributes.getClassArray(EnableCompose.METHOD_NAME);
        if (composeClassArray == null || composeClassArray.length == 0) return;

        // 循环需要处理的接口来生成对应的ComposeFactoryBean的BeanDefinition，并注册到注册中心
        for (Class<?> tClass : composeClassArray) {

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DEFAULT_FACTORY_BEAN_CLASS);
            // 设置ComposeFactoryBean构造方法参数值
            builder.addConstructorArgValue(tClass);
            // 设置方法增强的BeanName
            String methodAdviceBeanName = this.getComposeMethodAdviceBeanName(tClass, registry);
            builder.addConstructorArgReference(methodAdviceBeanName);
            // 根据当前循环的接口创建对应的ComposeFactoryBean的BeanDefinition
            BeanDefinition beanDefinition = builder.getBeanDefinition();

            // 这里需要缓存一下生成的ComposeFactoryBean Bean的Bean Name，用于后续ComposeFactory找到对应的Bean
            String beanName = beanNameCache.computeIfAbsent(tClass, this::buildFactoryBeanBeanName);
            // 往注册中心注册BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private String getComposeMethodAdviceBeanName(Class tClass, BeanDefinitionRegistry registry) {
        String specialComposeMethodAdviceBeanName = Introspector.decapitalize(
                tClass.getSimpleName() + ComposeMethodAdvice.class.getSimpleName());
        return registry.containsBeanDefinition(specialComposeMethodAdviceBeanName) ?
                specialComposeMethodAdviceBeanName : DEFAULT_COMPOSE_METHOD_ADVICE_BEAN_NAME;
    }

    private String buildFactoryBeanBeanName(Class tClass) {
        return DEFAULT_FACTORY_BEAN_CLASS.getSimpleName() + "4" + tClass.getSimpleName();
    }

    static String getBeanName(Class tClass) {
        return beanNameCache.get(tClass);
    }

    static Set<Class> getAllTargetClass() {
        return beanNameCache.keySet();
    }
}
