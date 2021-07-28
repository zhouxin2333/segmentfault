package cool.zhouxin.q_1010000040378204.cache;

import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/28 9:59
 */
@Component
public class TestCacheImpl implements TestCache {

    @Override
    @CustomCache(key = "custom", timeout = 10l)
    public String say() {
        return "haha";
    }
}
