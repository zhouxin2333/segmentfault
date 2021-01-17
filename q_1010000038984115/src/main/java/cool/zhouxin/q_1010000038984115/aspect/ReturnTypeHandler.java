package cool.zhouxin.q_1010000038984115.aspect;

import org.springframework.core.ResolvableType;

/**
 * @author zhouxin
 * @since 2021/1/17 10:35
 */
public interface ReturnTypeHandler {

    boolean isNeedProcess(ResolvableType returnType);

    boolean isEmpty(Object o);

    Class getRawClass(Object o);

    Object newInstance(Class newClass, Object origin);

    default boolean isBaseVO(Class tClass) {
        return IBaseVO.class.isAssignableFrom(tClass);
    }

    default boolean isBaseVO(ResolvableType type) {
        return this.isBaseVO(type.getRawClass());
    }
}
