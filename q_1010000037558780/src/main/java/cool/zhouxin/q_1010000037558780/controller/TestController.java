package cool.zhouxin.q_1010000037558780.controller;

import cool.zhouxin.q_1010000037558780.api.pojo.TestRequest;
import cool.zhouxin.q_1010000037558780.api.pojo.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author zhouxin
 * @since 2020/10/22 10:30
 */
@RestController
public class TestController {

    @PostMapping(value = "test")
    public String get(@RequestBody TestRequest request) {
        System.out.println(request);
        return "ok test";
    }

    @GetMapping(value = "test")
    public TestResponse get() {
        TestResponse response = new TestResponse();
        response.setDate(LocalDateTime.now());
        return response;
    }
}
