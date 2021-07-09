package cool.zhouxin.q_1010000040291832.pojo;

import cool.zhouxin.q_1010000040291832.validation.CheckConsistency;
import lombok.Data;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@Data
@CheckConsistency
public class UserRequest {

    private String password;
    private String passwordAgain;
}
