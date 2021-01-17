package cool.zhouxin.q_1010000038984115.controller;

import cool.zhouxin.q_1010000038984115.aspect.IBaseVO;
import cool.zhouxin.q_1010000038984115.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouxin
 * @since 2021/1/16 19:37
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public IBaseVO detail() {
        IBaseVO userVO = userService.detail();
        return userVO;
    }

    @GetMapping("/users")
    public List<IBaseVO> list() {
        List<IBaseVO> userVOList = userService.list();
        return userVOList;
    }

    @GetMapping("/users/array")
    public IBaseVO[] listArray() {
        IBaseVO[] iBaseVOS = userService.listArray();
        return iBaseVOS;
    }
}
