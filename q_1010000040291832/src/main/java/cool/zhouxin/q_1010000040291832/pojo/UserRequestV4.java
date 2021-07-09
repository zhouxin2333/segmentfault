package cool.zhouxin.q_1010000040291832.pojo;

import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV2;
import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV2List;
import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV4;
import cool.zhouxin.q_1010000040291832.validation.ConsistencyGroup;
import cool.zhouxin.q_1010000040291832.validation.custom.NameConsistency;
import cool.zhouxin.q_1010000040291832.validation.custom.PasswordConsistency;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@Data
@CheckConsistencyV4
public class UserRequestV4 {

    @PasswordConsistency
    private String password;
    @PasswordConsistency
    private String passwordAgain;
    @NameConsistency
    private String name;
    @NameConsistency
    private String nameAgain;
}
