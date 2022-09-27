package edu.fk.mapper;

import edu.fk.pojo.Route;
import edu.fk.pojo.RouteImg;

import java.util.List;
import java.util.Map;

/**
 * 功能： 1、支持根据分类进行查询  2、支持分页查询
 */

public interface RouteMapper {
    //查询  传参方式，map的key要跟sql的对象名一致
    public List<Route> selectByCidAndName(Map params);
    public List<Route> selectByCondition(Map params);
    //增加
    public int insert(Route route);
    //删除  根据id，将记录变成删除状态
    public int updateToDeleteById(int rid);
    //编辑
    public void update(Route route);

    //热门旅游线路+收藏排行榜
    public List<Route> selectHot(Integer limit);

    //旅游产品详情页
    Route selectById(Integer rid);
    public List<RouteImg> selectByRid(Integer rid);

    //收藏和取消收藏
    public void updateCountAdd(Integer rid);
    public void updateCountSub(Integer rid);

}
