package com.example.q_1010000039709273;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * @author zhouxin
 * @since 2021/3/26 23:21
 */
@Data
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private String prefix;
    private Resource resource;

    private static final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY =
            new DefaultPropertySourceFactory();
    private static final String PROPERTY_SOURCE_NAME =
            MyBeanDefinitionRegistryPostProcessor.class.getSimpleName() + "_properties";

    public void init() {
        PropertySource propertySource = this.buildPropertySource();
        this.prefix = propertySource.getProperty("myprefix").toString();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // user加上前缀变为asdfuser
        String originalName = "user";
        String newName = this.prefix + originalName;
        BeanDefinition user = registry.getBeanDefinition(originalName);
        // 改别名映射关系
        String[] users = registry.getAliases(originalName);
        for (String s : users) {
            registry.registerAlias(newName, s);
        }
        // 修改父bean的名字的为newName
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            String parentName = beanDefinition.getParentName();
            if (originalName.equals(parentName)) {
                beanDefinition.setParentName(newName);
            }
        }
        // 删除originalName的BeanDefinition，以newName重新注册BeanDefinition
        registry.removeBeanDefinition(originalName);
        registry.registerBeanDefinition(newName, user);
    }

    private PropertySource buildPropertySource() {
        PropertySource<?> propertySource = null;
        try {
            propertySource = DEFAULT_PROPERTY_SOURCE_FACTORY.createPropertySource(PROPERTY_SOURCE_NAME,
                    new EncodedResource(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertySource;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
