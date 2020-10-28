package cool.zhouxin.q_1010000037633610.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author zhouxin
 * @since 2020/10/28 16:06
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ApproveCustomerEntity {

    @NonNull
    private String ageGroup;
    private Integer peopleNum = Integer.valueOf(0);
    private Integer passNum = Integer.valueOf(0);
    private Integer rejectNum = Integer.valueOf(0);

}
