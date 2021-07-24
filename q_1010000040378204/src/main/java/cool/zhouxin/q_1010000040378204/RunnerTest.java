package cool.zhouxin.q_1010000040378204;

import cool.zhouxin.q_1010000040378204.observer.ObserverCompose;
import cool.zhouxin.q_1010000040378204.observer.ObserverManager;
import cool.zhouxin.q_1010000040378204.observer.ObserverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/7/24 16:25
 */
@Component
public class RunnerTest implements ApplicationRunner {
    @Autowired
    ObserverManager observerManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObserverCompose observerCompose = observerManager.include(ObserverType.A, ObserverType.B);
        observerCompose.del();
        System.out.println("========================");
        ObserverCompose observerCompose2 = observerManager.include(ObserverType.A);
        observerCompose2.del();

        ObserverCompose observerCompose3 = observerManager.include(ObserverType.A, ObserverType.B);
        System.out.println(observerCompose == observerCompose3);
    }
}
