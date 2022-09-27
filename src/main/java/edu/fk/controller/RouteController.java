package edu.fk.controller;

import edu.fk.constants.MessageConstant;
import edu.fk.constants.RedisConstant;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.entity.Result;
import edu.fk.pojo.Route;
import edu.fk.pojo.RouteImg;
import edu.fk.service.RouteService;
import edu.fk.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RouteService routeService;

    @Autowired
    JedisPool jedisPool;

    //路径映射
    @RequestMapping("/queryPageByCondition")
    public PageResult<Route> queryPageByCondition(int cid, QueryPageBean pageBean) {
        return routeService.queryByCidAndCondition(cid,pageBean);
    }

    //查询旅游线路
    @RequestMapping("/queryByCondition")
    public PageResult<Route> queryByCondition(@RequestBody QueryPageBean pageBean){
        return routeService.queryByCondition(pageBean);
    };

    //删除旅游线路
    @RequestMapping("/delete")
    public Result deleteRoute(int rid){
        int ret = routeService.updateToDeleteById(rid);
        return new Result((ret>0)?true:false,"");
    }

    //编辑线路信息
    @RequestMapping("/queryOne")
    public Result queryOne(Integer rid) {
        try {
            Route route = routeService.queryDetail(rid);
            return new Result(true, MessageConstant.QUERY_ROUTE_SUCCESS, route);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROUTE_FAIL);
        }
    }

    //修改旅游线路
    @RequestMapping("/modify")
    public Result modify(@RequestBody Route route) {
        try {
            routeService.modify(route);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_ROUTE_FAIL);
        }
        return new Result(true, MessageConstant.UPDATE_ROUTE_SUCCESS);
    }

    //增加旅游线路
    @RequestMapping("/addRoute")
    public Result addRoute(@RequestBody Route route){
        int insertRet = routeService.insert(route);
        return new Result(true,"");
    }

    //本地文件上传的nginx支持
    //注意参数名要和表单的name的值一致
    @RequestMapping("/upload")
    public String upload(MultipartFile imgFile){
        try {
            imgFile.transferTo(new File("D:\\Program Files (x86)\\nginx-1.23.0\\html\\img\\"+imgFile.getOriginalFilename()));
        }catch (IOException e){
            e.printStackTrace();
        }
        return "http://127.0.0.1/img/"+imgFile.getOriginalFilename();
    }

    //七牛云上传
    @RequestMapping("/upload7niu")
    public String upload7niu(MultipartFile imgFile) {
        String key = "img/"+ UUID.randomUUID().toString();
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), key);
            //将图片名保存到redis指定的集合中
            jedisPool.getResource().sadd(RedisConstant.ROUTE_IMG_ALL,key);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "http://ri6w7imqj.hn-bkt.clouddn.com/"+key; //地址过期需要修改
    }

    //热门旅游线路+收藏排行榜
    @RequestMapping("/queryHot")
    public Result queryHot(Integer limit) {
        try {
            List<Route> routeList = routeService.queryHot(limit);
            return new Result(true, "", routeList);
        }catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }
    }

    //旅游产品详情页及图片
    @RequestMapping("/queryDetail")
    public Result queryDetail(Integer rid) {
        try {
            Route route = routeService.queryDetail(rid);
            return new Result(true, "", route);
        }catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }
    }
    @RequestMapping("/queryRouteImage")
    public Result queryRouteImage(Integer rid) {
        try {
            List<RouteImg> routeImgList = routeService.queryRouteImage(rid);
            return new Result(true, "", routeImgList);
        }catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }
    }
}
