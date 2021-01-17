package cool.zhouxin.q_1010000038984115.aspect;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/1/17 10:35
 */
@Component
public class ReturnTypePOJOHandler implements ReturnTypeHandler {

    @Override
    public boolean isNeedProcess(ResolvableType returnType) {
        // 这里判断是否是一个POJO，继承要求的接口就是POJO对象
        return this.isBaseVO(returnType);
    }

    @Override
    public boolean isEmpty(Object o) {
        return o == null;
    }

    @Override
    public Class getRawClass(Object o) {
        return o.getClass();
    }

    @Override
    public Object newInstance(Class newClass, Object origin) {
        Object newInstance = null;
        try {
            newInstance = newClass.newInstance();
            if (origin == null) return newInstance;
            BeanUtils.copyProperties(origin, newInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
