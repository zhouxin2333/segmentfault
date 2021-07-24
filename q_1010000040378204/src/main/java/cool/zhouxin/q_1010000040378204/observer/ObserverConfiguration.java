package cool.zhouxin.q_1010000040378204.observer;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zhouxin
 * @since 2021/7/24 22:41
 */
@Configuration
@EnableAsync
public class ObserverConfiguration implements AsyncConfigurer {

    /**
     * 如果你想配置@Async注解自己的Executor，可以实现{@link #getAsyncExecutor()}进行配置
     * 而默认的Executor配置在这里{@link TaskExecutorFactoryBean#afterPropertiesSet}
     * @return
     */
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(7);
//        executor.setMaxPoolSize(42);
//        executor.setQueueCapacity(11);
//        executor.setThreadNamePrefix("Observer-Executor-");
//        return executor;
//    }
}
