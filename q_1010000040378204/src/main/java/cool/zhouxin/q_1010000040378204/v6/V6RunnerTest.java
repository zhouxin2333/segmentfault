package cool.zhouxin.q_1010000040378204.v6;

import cool.zhouxin.q_1010000040378204.v6.compose.ComposeFactory;
import cool.zhouxin.q_1010000040378204.v6.compose.example.CleanRoom;
import cool.zhouxin.q_1010000040378204.v6.compose.example.CookFood;
import cool.zhouxin.q_1010000040378204.v6.compose.example.CookFoodType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/24 16:25
 */
@Component
public class V6RunnerTest implements ApplicationRunner, BeanFactoryAware {

    @Autowired
    private ComposeFactory composeFactory;
    BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CookFood cookFood = composeFactory.create(CookFood.class);
        cookFood.xiCai("zx");

        System.out.println("==========================");

        CookFood cookFood1 = composeFactory.create(CookFood.class, CookFoodType.A, CookFoodType.B);
        cookFood1.zhuCai(10l, "haha");

        System.out.println("=======================");

        CookFood cookFood3 = composeFactory.create(CookFood.class);
        System.out.println(cookFood == cookFood3);

        System.out.println("=======================");

        CookFood cookFoodExclude1 = composeFactory.createByExclude(CookFood.class, CookFoodType.A);
        cookFoodExclude1.zuoFan();


        CleanRoom cleanRoom = composeFactory.create(CleanRoom.class);
        cleanRoom.tuoDi();
    }
}
