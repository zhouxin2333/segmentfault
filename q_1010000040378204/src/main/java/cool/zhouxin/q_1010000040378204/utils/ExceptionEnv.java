package cool.zhouxin.q_1010000040378204.utils;

import cool.zhouxin.q_1010000040378204.function.ExceptionRunnable;
import cool.zhouxin.q_1010000040378204.function.ExceptionSupplier;

/**
 * @author zhouxin
 * @since 2021/7/24 17:47
 */
public class ExceptionEnv {

    public static void handler(ExceptionRunnable runnable) {
        try {
            runnable.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handler(ExceptionRunnable... runnableList) {
        try {
            for (ExceptionRunnable runnable : runnableList) {
                runnable.run();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T handler(ExceptionSupplier<T> supplier) {
        try {
            return supplier.get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
