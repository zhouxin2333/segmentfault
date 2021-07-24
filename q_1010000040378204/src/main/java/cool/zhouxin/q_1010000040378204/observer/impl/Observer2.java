package cool.zhouxin.q_1010000040378204.observer.impl;

import cool.zhouxin.q_1010000040378204.observer.Observer;
import cool.zhouxin.q_1010000040378204.observer.ObserverExecutor;
import cool.zhouxin.q_1010000040378204.observer.ObserverType;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/24 16:00
 */
@Component
public class Observer2 implements ObserverExecutor {

    @Override
    public ObserverType type() {
        return ObserverType.B;
    }

    @Override
    public void del() {
        System.out.println("2号观察者进入删除方法。。。。");
    }

    @Override
    public void inl(String name) {
        System.out.println("2号观察者进入新增方法。。。。");
    }

    @Override
    public void up(Long id, String name) {
        System.out.println("2号观察者进入更新方法。。。。");
    }

    @Override
    public void get(Long id) {
        System.out.println("2号观察者进入查询方法。。。。");
    }
}
