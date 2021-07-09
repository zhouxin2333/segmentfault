package cool.zhouxin.q_1010000040291832.pojo;

import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV3;
import cool.zhouxin.q_1010000040291832.validation.ConsistencyGroup;
import lombok.Data;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@Data
@CheckConsistencyV3
public class UserRequestV3 {

    @ConsistencyGroup
    private String password;
    @ConsistencyGroup
    private String passwordAgain;
}
