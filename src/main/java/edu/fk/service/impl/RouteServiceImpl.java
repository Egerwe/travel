package edu.fk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.fk.constants.RedisConstant;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.mapper.RouteMapper;
import edu.fk.pojo.Route;
import edu.fk.pojo.RouteImg;
import edu.fk.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service //注册成为service
@Transactional //事物支持
public class RouteServiceImpl implements RouteService {
    //注入依赖的Mapper
    @Autowired
    RouteMapper routeMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public PageResult<Route> queryByCidAndCondition(int cid, QueryPageBean queryPageBean){
        //翻页处理
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //mapper调用时候参数的构建
        Map param = new HashMap<>();
        param.put("cid",cid);//参数名要和sql语句中的参数一致
        param.put("rname",queryPageBean.getQueryString());

        //翻页后的数据类型是Page
        Page<Route> routesPage = (Page<Route>) routeMapper.selectByCidAndName(param);

        //返回的数据的构建PageResult。  getTotal() --- 获取总的条数：getResult() -- 返回查询结果
        return new PageResult<Route>(routesPage.getTotal(),routesPage.getResult());
    }

    @Override
    public PageResult<Route> queryByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Map param = new HashMap<>();
        param.put("rname",queryPageBean.getQueryString());

        Page<Route> routesPage = (Page<Route>) routeMapper.selectByCondition(param);
        return new PageResult<Route>(routesPage.getTotal(),routesPage.getResult());
    }

    @Override
    public int insert(Route route) {
        //冗余图片处理 //todo
        //将图片名保存到redis指定的集合中
        String imgFileName = route.getRimage();
        imgFileName = imgFileName.substring(imgFileName.indexOf("img"));
        jedisPool.getResource().sadd(RedisConstant.ROUTE_IMG_DB,route.getRimage());
        return routeMapper.insert(route);
    }

    @Override
    public int updateToDeleteById(int rid) {
        return routeMapper.updateToDeleteById(rid);
    }

    @Override
    public void modify(Route route) {
        // 日期内容截取
        route.setRdate(route.getRdate().substring(0, 10));
        routeMapper.update(route);
    }

    //热门旅游线路+收藏排行榜
    @Override
    public List<Route> queryHot(Integer limit) {
        return routeMapper.selectHot(limit);
    }

    //旅游产品详情页
    @Override
    public Route queryDetail(Integer rid) {
        return routeMapper.selectById(rid);
    }
    @Override
    public List<RouteImg> queryRouteImage(Integer rid) {
        return routeMapper.selectByRid(rid);
    }
}
