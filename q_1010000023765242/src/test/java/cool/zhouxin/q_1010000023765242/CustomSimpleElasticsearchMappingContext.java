package cool.zhouxin.q_1010000023765242;

import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2020/10/15 18:24
 */
@Component
public class CustomSimpleElasticsearchMappingContext extends SimpleElasticsearchMappingContext {

    @Override
    protected ElasticsearchPersistentProperty createPersistentProperty(Property property, SimpleElasticsearchPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new CustomSimpleElasticsearchPersistentProperty(property, owner, simpleTypeHolder);
    }
}
