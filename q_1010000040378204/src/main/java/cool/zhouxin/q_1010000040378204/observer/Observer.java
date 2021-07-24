package cool.zhouxin.q_1010000040378204.observer;

public interface Observer {

     // 删除操作
     void del();

     // 新增操作
     void inl(String name);

     // 更新操作
     void up(Long id, String name);

     // 获取操作
     void get(Long id);
}
