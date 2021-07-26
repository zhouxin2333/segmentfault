package cool.zhouxin.q_1010000040378204.v6.compose;

import java.lang.reflect.Method;

/**
 * @author zhouxin
 * @since 2021/7/26 17:10
 */
class DefaultComposeItemExceptionHandler implements ComposeItemExceptionHandler {

    static DefaultComposeItemExceptionHandler INSTANCE = new DefaultComposeItemExceptionHandler();

    @Override
    public void process(Exception e, Object o, Method method, Object objects) {
        System.out.println(o.getClass().getSimpleName() + "-" + method.getName() + "：出错啦，" + e.getMessage());
    }
}
