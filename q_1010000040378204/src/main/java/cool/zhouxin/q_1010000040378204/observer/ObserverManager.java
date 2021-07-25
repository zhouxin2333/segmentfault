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
public class ObserverManager implements BeanFactoryAware {

    // 所有的ObserverExecutor集合
    @Autowired
    private List<ObserverExecutor> executors;
    private BeanFactory beanFactory;
    private static final ObserverType[] ALL_TYPES = ObserverType.values();
    // 用于缓存ObserverType组合对应的ObserverCompose
    private Map<String, ObserverCompose> observerComposeMap = new HashMap<>();

    /**
     * 如果includeTypes为空，则表示包含所有的ObserverType
     * @param includeTypes
     * @return
     */
    public ObserverCompose include(ObserverType... includeTypes) {
        // includeTypes的空处理，如果传入为空，默认处理为包含所有ObserverType
        ObserverType[] includeTypesAfter = this.processEmpty(includeTypes);
        // 方便缓存，把includeTypes按照字母顺序排序然后取name拼接起来作为cacheKey
        String cacheKey = this.buildCacheKey(includeTypesAfter);
        // 根据cacheKey去observerComposeMap缓存中获取，如果不存在，则用当前的includeTypesAfter去重新获取一个
        ObserverCompose observerCompose = observerComposeMap.computeIfAbsent(cacheKey,
                key -> this.createCompose(includeTypesAfter));
        return observerCompose;
    }

    /**
     * 如果excludeTypes为空，则表示包含所有的ObserverType
     * @param excludeTypes
     * @return
     */
    public ObserverCompose exclude(ObserverType... excludeTypes) {
        if (excludeTypes == null || excludeTypes.length == 0) return this.include(ALL_TYPES);

        Set<ObserverType> excludeTypesSet = Stream.of(excludeTypes).collect(Collectors.toSet());
        ObserverType[] includeTypes = Stream.of(ALL_TYPES)
                                            .filter(observerType -> !excludeTypesSet.contains(observerType))
                                            .toArray(ObserverType[]::new);
        return this.include(includeTypes);
    }

    private ObserverType[] processEmpty(ObserverType[] includeTypes) {
        return includeTypes == null || includeTypes.length == 0 ? ALL_TYPES : includeTypes;
    }

    private ObserverCompose createCompose(ObserverType[] includeTypes) {
        // 由于ObserverCompose是多例，所以现在从beanFactory中获取的会是一个新的Bean
        ObserverCompose observerCompose = beanFactory.getBean(ObserverCompose.class);
        // 因为ObserverCompose的实现类是继承了DefaultObserverContainer，所以这里可以强转
        ObserverContainer observerContainer = ObserverContainer.class.cast(observerCompose);
        // 根据当前传入的includeTypes与所有的ObserverExecutor集合executors进行过滤，筛选出满足条件的executor
        List<ObserverExecutor> includeObservers = this.filterIncludeObservers(includeTypes);
        // 满足条件的executor直接注入到observerContainer中
        observerContainer.register(includeObservers);
        // observerCompose该bean构造完毕，直接返回
        return observerCompose;
    }

    private List<ObserverExecutor> filterIncludeObservers(ObserverType[] includeTypes) {
        // includeTypes数组转换成Set，利用hash判断效率更高
        Set<ObserverType> includeTypesSet = Stream.of(includeTypes).collect(Collectors.toSet());
        return this.executors.stream()
                             .filter(executor -> includeTypesSet.contains(executor.type()))
                             .collect(Collectors.toList());
    }

    private String buildCacheKey(ObserverType[] includeTypes) {
        String cacheKey = Stream.of(includeTypes).map(Enum::name).sorted().collect(Collectors.joining("-"));
        return cacheKey;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
