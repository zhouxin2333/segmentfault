package cool.zhouxin.q_1010000040378204;

import cool.zhouxin.q_1010000040378204.cache.TestCacheConfiguration;
import cool.zhouxin.q_1010000040378204.observer.ObserverConfiguration;
import cool.zhouxin.q_1010000040378204.v6.compose.V6ComposeConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackageClasses = {
        ObserverConfiguration.class,
        V6ComposeConfiguration.class,
        TestCacheConfiguration.class
//        ComposeConfiguration.class
})
public class Q1010000040378204Application {

    public static void main(String[] args) {
        SpringApplication.run(Q1010000040378204Application.class, args);
    }

}
