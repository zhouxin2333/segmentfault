package cool.zhouxin.q_1010000038984115.service;

import cool.zhouxin.q_1010000038984115.aspect.FieldLimit;
import cool.zhouxin.q_1010000038984115.aspect.IBaseVO;
import cool.zhouxin.q_1010000038984115.pojo.UserVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/1/16 19:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @FieldLimit
    public IBaseVO detail() {
        return this.get();
    }

    @Override
    @FieldLimit({"id", "name", "area"})
    public List<IBaseVO> list() {
        return this.getList();
    }

    @Override
    @FieldLimit({"id", "name"})
    public IBaseVO[] listArray() {
        return new IBaseVO[]{this.get()};
    }

    public UserVO get() {
        return new UserVO(1l, "小李", "123456", "四川");
    }
    public List<IBaseVO> getList() {
        return Arrays.asList(
                new UserVO(1l, "小李", "123456", "四川"),
                new UserVO(2l, "小张", "654321", "重庆"));
    }
}
