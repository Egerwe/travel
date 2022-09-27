package edu.fk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //注册为一个controller
@RequestMapping("/test")
public class TestController {
    //访问路径： http://ip:port/travel/test/hellow.do
    @RequestMapping(value = "/hello",produces = "application/json; charset=utf-8")
    public String sayHello(){
        return "你好,佛山!";
    }
}
