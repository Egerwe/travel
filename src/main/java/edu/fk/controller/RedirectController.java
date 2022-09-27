package edu.fk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test2")
public class RedirectController {

    //测试请求转发
    @RequestMapping("/forward")
    public String forward() {
        return "/pages/login.html";
    }

    //测试重定向
    @RequestMapping("redirect")
    public String redirect(HttpServletResponse response) {
        return "redirect:/pages/login.html";
    }
}