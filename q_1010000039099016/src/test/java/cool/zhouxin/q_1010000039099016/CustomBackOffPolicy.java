package cool.zhouxin.q_1010000039099016;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author zhouxin
 * @since 2021/1/27 16:38
 */
@NoArgsConstructor(staticName = "init")
    public class CustomBackOffPolicy implements SleepingBackOffPolicy<CustomBackOffPolicy> {

    private Queue<Long> intervalQueue = new LinkedList<>();
    @Setter
    private Sleeper sleeper = new ThreadWaitSleeper();

    public CustomBackOffPolicy addInterval(Long interval) {
        this.intervalQueue.offer(interval);
        return this;
    }

    public CustomBackOffPolicy addIntervals(Long ... intervalArray) {
        for (Long interval : intervalArray) {
            this.intervalQueue.offer(interval);
        }
        return this;
    }

    @Override
    public CustomBackOffPolicy withSleeper(Sleeper sleeper) {
        CustomBackOffPolicy customBackOffPolicy = new CustomBackOffPolicy();
        customBackOffPolicy.setSleeper(this.sleeper);
        return customBackOffPolicy;
    }

    @Override
    public BackOffContext start(RetryContext context) {
        return new CustomBackOffContext(this.intervalQueue);
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        CustomBackOffContext context = (CustomBackOffContext) backOffContext;
        try {
            long sleepTime = context.getSleep();
            this.sleeper.sleep(sleepTime);
        }
        catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
        }
    }

    @AllArgsConstructor
    static class CustomBackOffContext implements BackOffContext {
        private Queue<Long> intervalQueue;

        public synchronized long getSleep() {
            return intervalQueue.poll();
        }
    }
}
