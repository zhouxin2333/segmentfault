package com.example.q_1010000039709273;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author zhouxin
 * @since 2020/4/28 17:57
 */
public class Main_1010000039709273 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup-context.xml");

        User asdfuser = beanFactory.getBean("asdfuser", User.class);
        System.out.println(asdfuser);
        // 报错
//        User user = beanFactory.getBean("user", User.class);
    }
}
