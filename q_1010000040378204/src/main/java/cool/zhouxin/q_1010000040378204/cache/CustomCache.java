package cool.zhouxin.q_1010000040378204.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * @since 2021/7/28 9:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCache {

    // 这里的方法都是根据你们实际的情况，想怎么加就怎么加的，我这里只是举了个例子

    String key();

    long timeout();
}
