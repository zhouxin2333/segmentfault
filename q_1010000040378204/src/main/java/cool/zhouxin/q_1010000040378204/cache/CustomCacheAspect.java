package cool.zhouxin.q_1010000040378204.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/28 9:50
 */
@Component
@Aspect
public class CustomCacheAspect {

    @Around("@annotation(customCache)")
    public Object execute(ProceedingJoinPoint joinPoint, CustomCache customCache) {
        System.out.println("in cache aspect begin, current key: " + customCache.key());
        String key = customCache.key();
        // 根据key去缓存噻，有的话可以直接返回
        Object cacheData = this.findFromCache(key);
        if (cacheData != null) return cacheData;

        Object result = null;
        try {
            // 这是执行被注解的方法，比如查询了数据库
            result = joinPoint.proceed();
            // 查出来的结果可以放进缓存
            this.putCache(key, result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("in cache aspect end");
        return result;
    }
    private void putCache(String key, Object result) {
    }
    private Object findFromCache(String key) {
        return null;
    }
}
