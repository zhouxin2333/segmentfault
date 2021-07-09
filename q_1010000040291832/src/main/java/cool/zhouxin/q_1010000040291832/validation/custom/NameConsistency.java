package cool.zhouxin.q_1010000040291832.validation.custom;

import cool.zhouxin.q_1010000040291832.validation.ConsistencyTag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * @since 2021/7/9 21:30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ConsistencyTag
public @interface NameConsistency {

    String message() default "name不一致";
}
