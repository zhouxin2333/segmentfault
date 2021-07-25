package cool.zhouxin.q_1010000040378204.observer;

import org.springframework.scheduling.annotation.Async;

/**
 * 如果你想启动异步，就把对应的下面的@Async注释放开即可
 */
interface Observer {

     // 删除操作
//     @Async
     void del();

     // 新增操作
//     @Async
     void inl(String name);

     // 更新操作
//     @Async
     void up(Long id, String name);

     // 获取操作
//     @Async
     void get(Long id);
}
