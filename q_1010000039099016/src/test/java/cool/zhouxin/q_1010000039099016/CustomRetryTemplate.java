package cool.zhouxin.q_1010000039099016;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author zhouxin
 * @since 2021/1/27 18:18
 */
public class CustomRetryTemplate {

    private Queue<Long> intervalQueue = new LinkedList(Arrays.asList(1000l, 5000l, 10000l));

    public <T> T doRetry(Supplier<T> supplier) {
        try{
            return supplier.get();
        }catch (Exception e) {
            while (!intervalQueue.isEmpty()) {
                Long interval = intervalQueue.poll();
                try {
                    TimeUnit.MILLISECONDS.sleep(interval);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                try {
                    return supplier.get();
                }catch (Exception ex) {
                    if (intervalQueue.isEmpty()){
                        throw ex;
                    }
                }
            }
        }
        return null;
    }
}
