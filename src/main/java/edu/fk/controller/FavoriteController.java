package edu.fk.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fk.constants.MessageConstant;
import edu.fk.entity.Result;
import edu.fk.pojo.Favorite;
import edu.fk.pojo.Route;
import edu.fk.pojo.User;
import edu.fk.service.FavoriteService;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/add")
    public Result add(Integer rid, HttpSession session) {
        try {
            // 判断用户是否登录
            User user =  (User)session.getAttribute(MessageConstant.LOGIN_SESSION_USER);
            if(user == null) {
                return new Result(false, MessageConstant.USER_NOT_LOGIN);
            }

            favoriteService.add(rid, user.getUid(), new Date());
        } catch (Exception e) {
            return new Result(false, MessageConstant.FAVORITE_ADD_FAIL);
        }
        return new Result(true, MessageConstant.FAVORITE_ADD_SUCCESS);
    }

    @RequestMapping("/remove")
    public Result remove(Integer rid, HttpSession session) {
        try {
            // 判断用户是否登录
            User user = (User)session.getAttribute(MessageConstant.LOGIN_SESSION_USER);
            if(user == null) {
                return new Result(false, MessageConstant.USER_NOT_LOGIN);
            }

            favoriteService.remove(rid, user.getUid());
        } catch (Exception e) {
            return new Result(false, MessageConstant.FAVORITE_REMOVE_FAIL);
        }
        return new Result(true, MessageConstant.FAVORITE_REMOVE_SUCCESS);
    }

    @RequestMapping("/queryStatus")
    public Result queryStatus(Integer rid, HttpSession session) {
        // 判断用户是否登录
        User user = (User)session.getAttribute(MessageConstant.LOGIN_SESSION_USER);
        if(user == null) {
            return new Result(false, "");
        }

        Favorite favorite = favoriteService.queryStatus(rid, user.getUid());
        return new Result(true, "", favorite);
    }

    @RequestMapping("/queryUserAll")
    public Result queryUserAll(HttpSession session) {
        // 判断用户是否登录
        User user = (User)session.getAttribute(MessageConstant.LOGIN_SESSION_USER);
        if(user == null) {
            return new Result(false, "");
        }

        List<Route> routeList = favoriteService.queryUserAll(user.getUid());
        return new Result(true, "", routeList);
    }
}
