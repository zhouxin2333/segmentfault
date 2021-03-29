package com.example.q_1010000039709273;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 依赖查找示例
 * 1. 通过名称的方式来查找
 * 2.
 *
 * @author zhouxin
 * @since 2020/4/28 17:57
 */
public class Main {

    public static void main(String[] args) {
        // 配置XML配置文件
        // 启动Spring应用上下文
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup-context.xml");

        User asdfuser = beanFactory.getBean("asdfuser", User.class);
        System.out.println(asdfuser);
        // 报错
//        User user = beanFactory.getBean("user", User.class);
    }
}
