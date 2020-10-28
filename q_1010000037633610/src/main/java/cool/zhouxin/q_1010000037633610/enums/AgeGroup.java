package cool.zhouxin.q_1010000037633610.enums;

import cool.zhouxin.q_1010000037633610.utils.Range;
import cool.zhouxin.q_1010000037633610.utils.Ranges;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhouxin
 * @since 2020/10/28 17:45
 */
@Getter
@AllArgsConstructor
public enum AgeGroup {
    GROUP_1(Ranges.ofCloseLeftCloseRight(18, 25)),
    GROUP_2(Ranges.ofCloseLeftCloseRight(26, 30)),
    GROUP_3(Ranges.ofCloseLeftCloseRight(31, 35)),
    GROUP_4(Ranges.ofCloseLeftCloseRight(36, 40)),
    GROUP_5(Ranges.ofCloseLeftCloseRight(41, 45)),
    GROUP_6(Ranges.ofCloseLeftCloseRight(46, 50)),
    GROUP_7(Ranges.ofCloseLeftCloseRight(51, 65)),
    OTHER(Ranges.ofAlwaysMatch("其他")),

    ;

    private Range<Integer> range;

    public static AgeGroup doMatch(Integer integer) {
        return Arrays.stream(AgeGroup.values())
                     .filter(ageGroup -> ageGroup.getRange().isMatch(integer))
                     .findFirst()
                     .get();
    }
}
