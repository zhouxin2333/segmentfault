package cool.zhouxin.q_1010000039099016;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author zhouxin
 * @since 2021/1/27 14:59
 */
@Component
@Slf4j
public class HttpExecutor {

    @Retryable(value = NullPointerException.class, maxAttempts = 4,
            backoff = @Backoff(delay = 1000, multiplier = 3))
    public void doGet(String param) {
        log.info("send get request");
        int length = param.length();
    }

    public void doPost(String param) {
        log.info("send post request");
        int length = param.length();
    }
}
