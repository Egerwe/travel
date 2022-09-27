package edu.fk.controller;

import edu.fk.constants.MessageConstant;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.entity.Result;
import edu.fk.pojo.User;
import edu.fk.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Element;

import javax.servlet.http.HttpSession;
import java.util.List;

//注册为一个controller，只能返回json/string数据，需要js代码实现跳转
@RestController
@RequestMapping("/user") //url增加user
@CrossOrigin("*")//允许跨域访问
public class UserController {
    //自动注入，spring框架会自动注入对象
    @Autowired
    UserService userService;

    //检查用户名是否重复
    //Result 前后端 交互时候，后端返回数据
    @RequestMapping("/checkUsername")
    public Result checkUsername(String username){
        //调用服务层，实现查询

        User user = userService.checkUsername(username);
        if(user != null){
            //用户存在，返回错误
            return new Result(false,"用户已经存在，不允许注册");
        }else{
            return new Result(true,"");
        }
        //return new Result(true,"");
    }

    //注册用户
    //注意：user对象的属性必须和表单中对应的数据name要一致
    //session使用用来获取内存中的验证码的
    //user --- 表单数据，由springMVC框架根据表单的名字注入user对象
    //check ---表单数据，由springMVC框架注入
    //HttpSession session，由框架自动注入
    @RequestMapping("/regist")
    public Result regist(User user, String check, HttpSession session){
        System.out.println("session id:" +session.getId());
        //对验证码进行校验
        String codeInSession = (String) session.getAttribute(MessageConstant.CHECKCODE_SERVER);
        if(codeInSession == null || !check.equalsIgnoreCase(codeInSession)){
            return new Result(false,MessageConstant.REGIST_CHECK_FAIL);
        }
        //调用服务层注册用户
        try{
            userService.resister(user);
        }catch (Exception ex){
//            return new Result(false,MessageConstant.REGIST_USER_FAIL());
            return new Result(false,ex.getMessage());
        }

        //注册成功
        return new Result(true,MessageConstant.REGIST_USER_SUCCESS);
    }

    @RequestMapping("/login")
    public Result login(User user,String check,HttpSession session){
        //获取内存中的验证码
        String checkCodeInSession = (String) session.getAttribute(MessageConstant.CHECKCODE_SERVER);
        //从session中移除验证码
        session.removeAttribute(MessageConstant.CHECKCODE_SERVER);
        if(checkCodeInSession == null || !checkCodeInSession.equalsIgnoreCase(check)){
            return new Result(false,MessageConstant.REGIST_CHECK_FAIL);
        }

        //用户的验证：根据用户名和密码 查询用户
        User user1 = userService.login(user);
        if (user1 != null){
            //是否激活
            if (user1.getStatus().equals("N")){
                return new Result(false,MessageConstant.LOGINT_USER_FAIL_ACTIVE);
            }
        }else {
            return new Result(false,MessageConstant.LOGINT_USER_FAIL);
        }
        //登录成功
        //保存用户到session --- 登录状态跟踪
        session.setAttribute(MessageConstant.LOGIN_SESSION_USER,user1);

        //返回结果
        return new Result(true,null);
    }


    //登录信息的显示
    @RequestMapping("/queryLoginUser")
    public Result queryLoginUser(HttpSession session){
        //从session获取登录用户信息
        User loginUser = (User) session.getAttribute(MessageConstant.LOGIN_SESSION_USER);
        if(loginUser == null){
            return new Result(false,null);
        }else {
            return new Result(true,null,loginUser);
        }
    }

    //注销处理
    @RequestMapping("logout")
    public Result logout(HttpSession session){
        //清除登录状态
        session.removeAttribute(MessageConstant.LOGIN_SESSION_USER);
        session.invalidate();//清理session

        return new Result(true,"");
    }

    //查询全部用户
    //@RequestBody 是从requestBody中获取json数据
    @RequestMapping("/findAll")
    public PageResult findAll(@RequestBody QueryPageBean pageBean){
        //调用service实现用户查询
        PageResult pageResult = userService.findAll(pageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    public Result deleteUser(Integer uid){
        System.out.println("----uid:"+uid);
        int deleteRes = userService.delete(uid);
        if (deleteRes > 0)
            return new Result(true,"");
        else
            return new Result(false,"删除失败");
    }

    @RequestMapping("/add")
    public Result addUser(@RequestBody User user){
        try{
            int ret = userService.add(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
        return new Result(true,"");
    }

    @RequestMapping("/update")
    public Result updateUser(@RequestBody User user){
        int ret = userService.update(user);
        if (ret > 0)
            return new Result(true,"");
        else
            return new Result(false,"更新失败");
    }
}