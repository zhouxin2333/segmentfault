package cool.zhouxin.q_1010000039099016;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@SpringBootTest
@EnableRetry
class Q1010000039099016ApplicationTests {

    @Autowired
    HttpExecutor httpExecutor;
    @Autowired
    RetryTemplate retryTemplate;

    @Test
    void doRetryByAnnotation() {
        httpExecutor.doGet(null);
    }

    @Test
    void doRetryByRetryTemplate() {
        retryTemplate.execute(context -> {
            httpExecutor.doPost(null);
            return null;
        });
    }

    @Test
    void doRetryByCustomRetryTemplate() {
       new CustomRetryTemplate().doRetry(() -> {
        httpExecutor.doPost(null);
        return null;
       });
    }



}
