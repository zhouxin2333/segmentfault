package cool.zhouxin.q_1010000040378204.function;

/**
 * @author zhouxin
 * @since 2021/7/24 17:43
 */
public interface ExceptionSupplier<T> {

    T get() throws Exception;
}
