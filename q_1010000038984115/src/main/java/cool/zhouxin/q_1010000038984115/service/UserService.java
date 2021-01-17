package cool.zhouxin.q_1010000038984115.service;

import cool.zhouxin.q_1010000038984115.aspect.IBaseVO;

import java.util.List;

/**
 * @author zhouxin
 * @since 2021/1/15 17:12
 */
public interface UserService {

    IBaseVO detail();

    List<IBaseVO> list();

    // 这个返回数组的是赠送的
    IBaseVO[] listArray();
}
