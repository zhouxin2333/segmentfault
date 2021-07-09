package cool.zhouxin.q_1010000040291832.pojo;

import cool.zhouxin.q_1010000040291832.validation.CheckConsistency;
import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV2;
import cool.zhouxin.q_1010000040291832.validation.CheckConsistencyV2List;
import lombok.Data;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@Data
@CheckConsistencyV2List({
        @CheckConsistencyV2(fieldName1 = "password", fieldName2 = "passwordAgain"),
        @CheckConsistencyV2(fieldName1 = "name", fieldName2 = "nameAgain")
})
public class UserRequestTemp {

    private String password;
    private String passwordAgain;
    private String name;
    private String nameAgain;
}
