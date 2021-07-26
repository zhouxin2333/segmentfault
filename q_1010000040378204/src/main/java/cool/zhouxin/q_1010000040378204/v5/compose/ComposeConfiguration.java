package cool.zhouxin.q_1010000040378204.v5.compose;

import cool.zhouxin.q_1010000040378204.v5.compose.example.CleanRoom;
import cool.zhouxin.q_1010000040378204.v5.compose.example.CookFood;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhouxin
 * @since 2021/7/25 16:25
 */
@Configuration
@EnableAsync
@EnableCompose({CookFood.class, CleanRoom.class})
@ComponentScan("cool.zhouxin.q_1010000040378204.v5")
public class ComposeConfiguration {

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
