package cool.zhouxin.q_1010000040378204.v6.compose;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/26 16:45
 */
public interface ComposeMethodAdvice<T> {

    /**
     * 当前方法增强所属的接口，若为null，则表示对所有需要实现Compose的接口的方法都会增强
     * @return
     */
    default Class<T> targetClass() {
        return null;
    }

    /**
     *
     * @param items 当前Compose中所有拥有的items
     * @param o Compose的代码对象
     * @param method 被代理的方法应用
     * @param objects 方法的参数
     * @return
     */
    Object invoke(List<T> items, Object o, Method method, Object[] objects);
}
