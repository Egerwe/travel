package edu.fk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fk.mapper.FavoriteMapper;
import edu.fk.mapper.RouteMapper;
import edu.fk.pojo.Favorite;
import edu.fk.pojo.Route;
import edu.fk.service.FavoriteService;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private RouteMapper routeMapper;

    @Override
    public void add(Integer rid, Integer uid, Date date) {
        Favorite favorite = new Favorite(rid, date.toString(), uid);
        // 增加favorite记录
        favoriteMapper.insert(favorite);
        // 更改route表中的count字段 +1
        routeMapper.updateCountAdd(rid);
    }

    @Override
    public void remove(Integer rid, Integer uid) {
        Favorite favorite = new Favorite(rid, null, uid);
        // 删除favorite记录
        favoriteMapper.delete(favorite);
        // 更改route表中的count字段 -1
        routeMapper.updateCountSub(rid);
    }

    @Override
    public Favorite queryStatus(Integer rid, Integer uid) {
        Favorite favorite = new Favorite(rid, null, uid);
        return favoriteMapper.selectByRidAndUid(favorite);
    }

    @Override
    public List<Route> queryUserAll(Integer uid) {
        return favoriteMapper.selectByUserId(uid);
    }
}
