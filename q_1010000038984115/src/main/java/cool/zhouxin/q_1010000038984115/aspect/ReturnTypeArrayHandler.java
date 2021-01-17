package cool.zhouxin.q_1010000038984115.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2021/1/17 10:35
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReturnTypeArrayHandler implements ReturnTypeHandler {

    @Autowired
    ReturnTypePOJOHandler pojoHandler;

    @Override
    public boolean isNeedProcess(ResolvableType returnType) {
        ResolvableType componentType = returnType.getComponentType();
        return returnType.isArray() && this.isBaseVO(componentType);
    }

    @Override
    public boolean isEmpty(Object o) {
        Object[] array = (Object[]) o;
        return array == null || array.length == 0;
    }

    @Override
    public Class getRawClass(Object o) {
        Object[] array = (Object[]) o;
        return pojoHandler.getRawClass(array[0]);
    }

    @Override
    public Object newInstance(Class newClass, Object origin) {
        IBaseVO[] array = (IBaseVO[]) origin;
        array = Arrays.stream(array).map(o -> (IBaseVO) pojoHandler.newInstance(newClass, o)).toArray(IBaseVO[]::new);
        return array;
    }
}
