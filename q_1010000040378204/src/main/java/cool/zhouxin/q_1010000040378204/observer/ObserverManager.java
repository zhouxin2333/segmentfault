package cool.zhouxin.q_1010000040378204.observer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/7/24 19:11
 */
@Component
public class ObserverManager implements BeanFactoryAware, SmartInitializingSingleton {

    @Autowired
    private List<ObserverExecutor> executors;
    private BeanFactory beanFactory;
    private Map<String, ObserverCompose> observerComposeMap = new HashMap<>();

    public ObserverCompose include(ObserverType... includeTypes) {
        String cacheKey = this.buildCacheKey(includeTypes);
        ObserverCompose observerCompose = observerComposeMap.computeIfAbsent(cacheKey,
                key -> this.createCompose(includeTypes));
        return observerCompose;
    }

    private ObserverCompose createCompose(ObserverType[] includeTypes) {

        ObserverCompose observerCompose = beanFactory.getBean(ObserverCompose.class);
        ObserverContainer observerContainer = ObserverContainer.class.cast(observerCompose);
        List<ObserverExecutor> includeObservers = this.filterIncludeObservers(includeTypes);
        observerContainer.register(includeObservers);
        return observerCompose;
    }

    private List<ObserverExecutor> filterIncludeObservers(ObserverType[] includeTypes) {
        Set<ObserverType> includeTypesSet = Stream.of(includeTypes).collect(Collectors.toSet());
        return this.executors.stream()
                             .filter(executor -> includeTypesSet.contains(executor.type()))
                             .collect(Collectors.toList());
    }

    private String buildCacheKey(ObserverType[] includeTypes) {
        if (includeTypes == null || includeTypes.length == 0) includeTypes = ObserverType.values();

        String cacheKey = Stream.of(includeTypes).map(Enum::name).sorted().collect(Collectors.joining("-"));
        return cacheKey;
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println(1);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
