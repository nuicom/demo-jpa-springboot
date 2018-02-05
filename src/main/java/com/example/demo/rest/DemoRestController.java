package com.example.demo.rest;

import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoRestController {

    @Autowired
    private DemoService demoService;

    @GetMapping(path = "/rest")
    public String getInfo() throws Exception {
        return "spring Demo" + demoService.findUserById().getClass().getName();
    }

//    @GetMapping(path = "/getUser")
//    public FwUserEntity getUser() throws Exception {
//        return demoService.findFwUserById();
//    }
}
