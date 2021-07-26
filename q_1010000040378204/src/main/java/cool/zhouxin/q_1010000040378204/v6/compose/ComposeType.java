package cool.zhouxin.q_1010000040378204.v6.compose;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记注解，用于标记当前接口所使用的枚举类型
 * @author zhouxin
 * @since 2021/7/25 17:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComposeType {

    Class<? extends Enum> value();
}
