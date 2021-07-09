package cool.zhouxin.q_1010000040291832.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhouxin
 * @since 2021/7/9 17:06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CheckConsistencyValidatorV2.class })
@Repeatable(CheckConsistencyV2List.class)
public @interface CheckConsistencyV2 {

    String fieldName1();

    String fieldName2();

    // 下面是三个属性是JVB规范的必带属性，没有会报错
    String message() default "不一致v2";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
