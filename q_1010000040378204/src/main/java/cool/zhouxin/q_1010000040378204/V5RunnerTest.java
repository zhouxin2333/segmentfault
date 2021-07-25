package cool.zhouxin.q_1010000040378204;

import cool.zhouxin.q_1010000040378204.v5.compose.ComposeFactory;
import cool.zhouxin.q_1010000040378204.v5.compose.example.CleanRoom;
import cool.zhouxin.q_1010000040378204.v5.compose.example.CookFoodType;
import cool.zhouxin.q_1010000040378204.v5.compose.example.CookFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouxin
 * @since 2021/7/24 16:25
 */
@Component
public class V5RunnerTest implements ApplicationRunner {

    @Autowired
    private ComposeFactory composeFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CookFood cookFood = composeFactory.get(CookFood.class);

        cookFood.xiCai("zx");

        System.out.println("==========================");

        CookFood cookFood1 = composeFactory.get(CookFood.class, CookFoodType.A, CookFoodType.B);
        cookFood1.zhuCai(10l, "haha");

        System.out.println("=======================");

        CookFood cookFood3 = composeFactory.get(CookFood.class);
        System.out.println(cookFood == cookFood3);

//        System.out.println("=======================");
//
//        CleanRoom cleanRoom = composeFactory.get(CleanRoom.class);
//        cleanRoom.tuoDi();
    }
}
