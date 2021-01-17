package cool.zhouxin.q_1010000038984115.pojo;

import cool.zhouxin.q_1010000038984115.aspect.IBaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhouxin
 * @since 2021/1/15 17:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements IBaseVO {

    private Long id;
    private String name;
    private String pwd;
    private String area;
}
