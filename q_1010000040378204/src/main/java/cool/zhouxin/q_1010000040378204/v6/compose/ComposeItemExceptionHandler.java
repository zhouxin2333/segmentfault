package cool.zhouxin.q_1010000040378204.v6.compose;

import java.lang.reflect.Method;

/**
 * Compose执行每一个item时的报错处理
 * @author zhouxin
 * @since 2021/7/26 17:00
 */
public interface ComposeItemExceptionHandler {

    /**
     * 当前调用的接口Class是否满足异常处理
     * @param tClass
     * @return
     */
    default boolean match(Class tClass) {
        return true;
    }

    /**
     *
     * @param e 异常
     * @param o 当前执行报错的item
     * @param method 当前执行报错的方法
     * @param objects 当前执行报错的方法参数
     */
    void process(Exception e, Object o, Method method, Object objects);
}
