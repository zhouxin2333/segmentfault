package cool.zhouxin.q_1010000040378204.v6.compose.example;

import cool.zhouxin.q_1010000040378204.v6.compose.example.NewCookFoodTypeMark;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/25 19:43
 */
@Component
@NewCookFoodTypeMark(CookFoodType.A)
public class CleanRoomImpl implements CleanRoom {

    @Override
    public void tuoDi() {
        System.out.println("我是Clean Room 1，开始拖地" );
    }
}
