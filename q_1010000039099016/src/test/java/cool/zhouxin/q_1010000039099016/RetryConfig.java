package cool.zhouxin.q_1010000039099016;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author zhouxin
 * @since 2021/1/27 16:13
 */
@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate defaultRetryTemplate() {
        Long[] intervals = {1000l, 5000l, 10000l};
        RetryTemplate retryTemplate = RetryTemplate.builder()
                .customBackoff(CustomBackOffPolicy.init().addIntervals(intervals))
                .customPolicy(new SimpleRetryPolicy(intervals.length + 1))
                .build();
        return retryTemplate;
    }
}
