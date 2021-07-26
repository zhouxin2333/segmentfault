package cool.zhouxin.q_1010000040378204.v6.compose;

import cool.zhouxin.q_1010000040378204.v6.compose.example.CookFood;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/26 18:40
 */
@Component
public class CookFoodComposeMethodAdvice implements ComposeMethodAdvice<CookFood> {

    @Override
    public Object invoke(List<CookFood> items, Object o, Method method, Object[] objects) {
        System.out.println("我CookFood 啥都不做，就是玩儿");
        return null;
    }
}
