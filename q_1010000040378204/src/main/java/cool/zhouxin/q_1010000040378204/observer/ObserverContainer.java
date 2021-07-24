package cool.zhouxin.q_1010000040378204.observer;

import java.util.List;

public interface ObserverContainer {

    void register(ObserverExecutor executor);
    void register(List<ObserverExecutor> executors);
}
