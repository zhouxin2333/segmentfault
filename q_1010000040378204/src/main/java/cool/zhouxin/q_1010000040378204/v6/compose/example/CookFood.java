package cool.zhouxin.q_1010000040378204.v6.compose.example;

import cool.zhouxin.q_1010000040378204.v6.compose.ComposeType;
import org.springframework.scheduling.annotation.Async;

/**
 * @author zhouxin
 * @since 2021/7/25 19:24
 */
@ComposeType(CookFoodType.class)
public interface CookFood {

//    @Async
    void xiCai(String name);
    void zhuCai(Long id, String name);
    void zuoFan();
}
