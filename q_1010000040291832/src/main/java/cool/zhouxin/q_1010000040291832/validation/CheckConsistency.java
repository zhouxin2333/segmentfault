package cool.zhouxin.q_1010000040291832.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * @since 2021/7/9 17:06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CheckConsistencyValidator.class })
public @interface CheckConsistency {

    // 下面是三个属性是JVB规范的必带属性，没有会报错
    String message() default "不一致";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
