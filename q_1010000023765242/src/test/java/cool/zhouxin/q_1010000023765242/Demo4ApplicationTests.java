package cool.zhouxin.q_1010000023765242;

import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Date;

@SpringBootTest
class Demo4ApplicationTests {

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Test
    void add() {
        DiscussPost post = new DiscussPost(1l, new Date());
        discussPostRepository.save(post);
    }

    @Test
    void contextLoads() {
        //构造查询条件
        NativeSearchQuery searchQuery =new NativeSearchQueryBuilder()
                //QueryBuilders 帮助类  multiMatchQuery 多条件匹配  第一个参数时要查询的文本 ，第二个参数时在那个字段里查询
//                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                //排序 按照type 这个字段排序，排序方式为倒叙
//                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                //分页
                .withPageable(PageRequest.of(0,10))
                //结果高亮显示
//                .withHighlightFields(
                        //高亮显示的字段      设置显示的标签
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                )
                .build();
        //查询
        Page<DiscussPost> search = discussPostRepository.search(searchQuery);
        System.out.println(search.getTotalElements()); //一共多少条数据
        System.out.println(search.getTotalPages()); //一共多少页
        System.out.println(search.getNumber());//当前在第几页
        System.out.println(search.getSize());//每页几条数据
        for (DiscussPost post : search) {
            //查看具体的数据
            System.out.println(post);
        }

    }

}
