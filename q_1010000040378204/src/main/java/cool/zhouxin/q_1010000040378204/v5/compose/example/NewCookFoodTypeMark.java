package cool.zhouxin.q_1010000040378204.v5.compose.example;

import cool.zhouxin.q_1010000040378204.v5.compose.ComposeTypeMeta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * @since 2021/7/25 19:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ComposeTypeMeta
public @interface NewCookFoodTypeMark {

    CookFoodType value();
}
