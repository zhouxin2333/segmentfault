package cool.zhouxin.q_1010000040378204.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/24 17:18
 */
class DefaultObserverContainer implements ObserverContainer {

    protected List<ObserverExecutor> executors = new ArrayList<>();

    @Override
    public void register(ObserverExecutor executor) {
        this.executors.add(executor);
    }

    @Override
    public void register(List<ObserverExecutor> executors) {
        this.executors = executors;
    }
}
