package cool.zhouxin.q_1010000040378204.v5.compose;


import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * @since 2021/7/25 16:17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ComposeFactoryBeanRegistrar.class)
public @interface EnableCompose {

    String METHOD_NAME = "value";

    /**
     * 需要启动组合模式执行的接口类型数组
     * @return
     */
    Class[] value();
}
