package cool.zhouxin.q_1010000040291832.pojo;

import cool.zhouxin.q_1010000040291832.validation.CheckConsistency;
import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV2;
import lombok.Data;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@Data
@CheckConsistencyV2(fieldName1 = "password", fieldName2 = "passwordAgain")
public class UserRequestV2 {

    private String password;
    private String passwordAgain;
}
