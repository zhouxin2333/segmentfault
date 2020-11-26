package coo.zhouxin.q_1010000038290113.controller;

import coo.zhouxin.q_1010000038290113.api.service.IDirtyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouxin
 * @since 2020/11/26 17:06
 */
@RestController
public class DirtyDataController {

    @Autowired
    IDirtyDataService service;

    @GetMapping("/dirty_data")
    public List<String> queryAll() {
        return service.all();
    }
}
