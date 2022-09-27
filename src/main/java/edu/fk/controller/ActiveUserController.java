package edu.fk.controller;

import edu.fk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//这种controller，需要controller决定跳转页面（请求转发和重定向都可以）
@Controller
@RequestMapping("/user")
public class ActiveUserController {
    @Autowired
    UserService userService;

    @RequestMapping("/active")
    public String activeUser(String code){
        //根据code进行激活码的验证
        System.out.println("激活码："+code);
        //调用服务层进行用户激活
        int ret = userService.activeUser(code);
        //根据激活的情况，重定向到激活成功或者激活失败页面
        if (ret >= 0)
            return  "redirect:/pages/active_ok.html";
        else
            return  "redirect:/pages/active_fail.html";
    }
}
