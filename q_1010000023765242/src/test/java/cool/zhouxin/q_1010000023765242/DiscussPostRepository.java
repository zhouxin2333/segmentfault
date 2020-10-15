package cool.zhouxin.q_1010000023765242;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhouxin
 * @since 2020/10/14 19:05
 */
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Long> {
}
