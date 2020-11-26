package coo.zhouxin.q_1010000038290113.api.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhouxin
 * @since 2020/11/26 16:24
 */
@Service
public class DirtyDataServiceImpl implements IDirtyDataService {

    @Override
    @Cacheable(cacheNames = {"EHCACHE_DIRTY_DATA", "REDIS_DIRTY_DATA"}, key = "'all_dirty_data'")
    public List<String> all() {
        return Arrays.asList("data1", "data2");
    }
}
