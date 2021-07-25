package cool.zhouxin.q_1010000040378204.observer;

import java.util.List;

interface ObserverContainer {

    void register(ObserverExecutor executor);
    void register(List<ObserverExecutor> executors);
}
