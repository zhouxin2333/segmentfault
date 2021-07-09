package cool.zhouxin.q_1010000040291832.controller;

import cool.zhouxin.q_1010000040291832.pojo.UserRequest;
import cool.zhouxin.q_1010000040291832.pojo.UserRequestV2;
import cool.zhouxin.q_1010000040291832.pojo.UserRequestV3;
import cool.zhouxin.q_1010000040291832.pojo.UserRequestV4;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zhouxin
 * @since 2021/7/9 16:20
 */
@RestController
public class UserController {

    @PostMapping("/user")
    @NotNull
    public Object user(@Valid @RequestBody UserRequest request) {
        return "ok";
    }

    @PostMapping("/user/v2")
    @NotNull
    public Object userV2(@Valid @RequestBody UserRequestV2 request) {
        return "ok";
    }

    @PostMapping("/user/v3")
    @NotNull
    public Object userV3(@Valid @RequestBody UserRequestV3 request) {
        return "ok";
    }

    @PostMapping("/user/v4")
    @NotNull
    public Object userV4(@Valid @RequestBody UserRequestV4 request) {
        return "ok";
    }
}
