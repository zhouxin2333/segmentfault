package cool.zhouxin.q_1010000040378204.observer.impl;

import cool.zhouxin.q_1010000040378204.observer.ObserverExecutor;
import cool.zhouxin.q_1010000040378204.observer.ObserverType;
import cool.zhouxin.q_1010000040378204.utils.ExceptionEnv;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouxin
 * @since 2021/7/24 16:00
 */
@Component
public class Observer1 implements ObserverExecutor {

    @Override
    public ObserverType type() {
        return ObserverType.A;
    }

    @Override
    public void del() {
        ExceptionEnv.handler(() -> TimeUnit.SECONDS.sleep(5));
        System.out.println("1号观察者进入删除方法。。。。");
    }

    @Override
    public void inl(String name) {
        System.out.println("1号观察者进入新增方法。。。。");
    }

    @Override
    public void up(Long id, String name) {
        System.out.println("1号观察者进入更新方法。。。。");
    }

    @Override
    public void get(Long id) {
        System.out.println("1号观察者进入查询方法。。。。");
    }
}
