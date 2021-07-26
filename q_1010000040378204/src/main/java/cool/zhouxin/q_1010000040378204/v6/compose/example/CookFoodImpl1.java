package cool.zhouxin.q_1010000040378204.v6.compose.example;

import cool.zhouxin.q_1010000040378204.v6.compose.example.NewCookFoodTypeMark;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/25 19:24
 */
@Component
@NewCookFoodTypeMark(CookFoodType.A)
public class CookFoodImpl1 implements CookFood {

    @Override
    public void xiCai(String name) {
//        ExceptionEnv.handler(() -> TimeUnit.SECONDS.sleep(5));
        throw new RuntimeException("哈哈啊哈啊");
//        System.out.println("我是Cook Food 1, 开始洗菜" + name);
    }

    @Override
    public void zhuCai(Long id, String name) {
        System.out.println("我是Cook Food 1, 开始煮菜" + id + "-" + name);

    }

    @Override
    public void zuoFan() {
        System.out.println("我是Cook Food 1, 开始做饭");
    }
}
