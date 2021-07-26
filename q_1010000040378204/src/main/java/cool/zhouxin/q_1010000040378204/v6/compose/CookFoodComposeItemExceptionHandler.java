package cool.zhouxin.q_1010000040378204.v6.compose;

import cool.zhouxin.q_1010000040378204.v6.compose.example.CookFood;

import java.lang.reflect.Method;

/**
 * @author zhouxin
 * @since 2021/7/26 19:14
 */
public class CookFoodComposeItemExceptionHandler implements ComposeItemExceptionHandler {

    @Override
    public boolean match(Class tClass) {
        return CookFood.class.equals(tClass);
    }

    @Override
    public void process(Exception e, Object o, Method method, Object objects) {

    }
}
