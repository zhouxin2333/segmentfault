package cool.zhouxin.q_1010000040378204.delete_modify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhouxin
 * @since 2021/8/17 17:10
 */
@AllArgsConstructor
@Getter
public enum DeleteModifyType {

    A(RichengType.CHONG_FU, ZuzhiType.ZUZHI, DataType.CURRENT),
    B(RichengType.CHONG_FU, ZuzhiType.ZUZHI, DataType.AFTER),
    C(RichengType.CHONG_FU, ZuzhiType.ZUZHI, DataType.ALL),
    D(RichengType.CHONG_FU, ZuzhiType.FEI_ZUZHI, DataType.CURRENT),
    E(RichengType.CHONG_FU, ZuzhiType.FEI_ZUZHI, DataType.AFTER),
    F(RichengType.CHONG_FU, ZuzhiType.FEI_ZUZHI, DataType.ALL),
    G(RichengType.FEI_CHONG_FU, ZuzhiType.ZUZHI, null),
    H(RichengType.FEI_CHONG_FU, ZuzhiType.FEI_ZUZHI, null),


    ;

    private RichengType richengType;
    private ZuzhiType zuzhiType;
    private DataType dataType;
}
