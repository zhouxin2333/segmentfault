package cool.zhouxin.q_1010000038984115.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2021/1/17 10:35
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReturnTypeCollectionHandler implements ReturnTypeHandler {

    @Autowired
    ReturnTypePOJOHandler pojoHandler;

    @Override
    public boolean isNeedProcess(ResolvableType returnType) {
        Class<?> rawClass = returnType.getRawClass();
        if (Collection.class.isAssignableFrom(rawClass)) {
            // 获取集合的类型，必须是IBaseVO或者是IBaseVO的子接口
            Class<?> genericRawClass = returnType.getGeneric().getRawClass();
            return this.isBaseVO(genericRawClass);
        }
        return false;
    }

    @Override
    public boolean isEmpty(Object o) {
        Collection collection = (Collection) o;
        return collection == null || collection.isEmpty();
    }

    @Override
    public Class getRawClass(Object o) {
        Collection collection = (Collection) o;
        Iterator iterator = collection.iterator();
        iterator.hasNext();
        return pojoHandler.getRawClass(iterator.next());
    }

    @Override
    public Object newInstance(Class newClass, Object origin) {
        List<?> list = (List) origin;
        list = list.stream().map(o -> pojoHandler.newInstance(newClass, o)).collect(Collectors.toList());
        return list;
    }
}
