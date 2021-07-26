package cool.zhouxin.q_1010000040378204.utils;

import java.lang.reflect.Array;
import java.util.function.IntFunction;

/**
 * @author zhouxin
 * @since 2021/7/26 11:01
 */
public class GenericArrayUtils {

    /**
     * 根据一个泛型数组创建一个泛型数组的初始化函数IntFunction
     * @param array
     * @param <T>
     * @return
     */
    public static <T> IntFunction<T[]> genericArray(T[] array) {
        return size -> (T[]) Array.newInstance(array.getClass().getComponentType(), size);
    }
}
