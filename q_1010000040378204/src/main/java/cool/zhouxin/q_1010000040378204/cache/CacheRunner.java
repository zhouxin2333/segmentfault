package cool.zhouxin.q_1010000040378204.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/28 9:59
 */
@Component
public class CacheRunner implements ApplicationRunner {

    @Autowired
    TestCache testCache;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testCache.say();
    }
}
