package cool.zhouxin.q_1010000040378204.v5.compose.example;

import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/25 19:24
 */
@Component
@NewCookFoodTypeMark(CookFoodType.C)
public class CookFoodImpl3 implements CookFood{

    @Override
    public void xiCai(String name) {
        System.out.println("我是Cook Food 3, 开始洗菜" + name);
    }

    @Override
    public void zhuCai(Long id, String name) {
        System.out.println("我是Cook Food 3, 开始煮菜" + id + "-" + name);

    }

    @Override
    public void zuoFan() {
        System.out.println("我是Cook Food 3, 开始做饭");
    }
}
