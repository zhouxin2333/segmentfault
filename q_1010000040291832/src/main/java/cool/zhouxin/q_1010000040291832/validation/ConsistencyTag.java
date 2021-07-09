package cool.zhouxin.q_1010000040291832.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被@ConsistencyTag 注解的注解，都需要有message方法来表明如果不一致
 * 应该怎样报错
 * @author zhouxin
 * @since 2021/7/9 18:08
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistencyTag {

    String METHOD_KEY = "message";
}
