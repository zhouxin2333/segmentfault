package cool.zhouxin.q_1010000040378204.v5.compose;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记注解，用于标记当前注解是一个用于标记类型的注解
 * 所有被该注解标记的注解，都需要提供一个方法名为value，返回枚举的方法
 * @author zhouxin
 * @since 2021/7/25 17:50
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComposeTypeMeta {

    String METHOD_NAME = "value";
}
