package edu.fk.service;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.pojo.Route;
import edu.fk.pojo.RouteImg;

import java.util.List;

public interface RouteService {
    //查询
    public PageResult<Route> queryByCidAndCondition(int cid, QueryPageBean queryPageBean);
    public PageResult<Route> queryByCondition(QueryPageBean queryPageBean);
    //增加
    public int insert(Route route);
    //删除 更新为删除状态
    public int updateToDeleteById(int rid);
    //编辑
    public void modify(Route route);

    //热门旅游线路+收藏排行榜
    List<Route> queryHot(Integer limit);

    //旅游产品详情页
    List<RouteImg> queryRouteImage(Integer rid);
    Route queryDetail(Integer rid);
}
