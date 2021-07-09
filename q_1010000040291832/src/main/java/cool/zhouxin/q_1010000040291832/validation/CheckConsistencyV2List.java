package cool.zhouxin.q_1010000040291832.validation;

import java.lang.annotation.*;

/**
 * @author zhouxin
 * @since 2021/7/9 21:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckConsistencyV2List {

    CheckConsistencyV2[] value();
}
