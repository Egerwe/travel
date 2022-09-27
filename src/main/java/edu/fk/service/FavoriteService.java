package edu.fk.service;

import edu.fk.pojo.Favorite;
import edu.fk.pojo.Route;

import java.util.Date;
import java.util.List;

public interface FavoriteService {

    public void add(Integer rid, Integer uid, Date date);

    public void remove(Integer rid, Integer uid);

    public Favorite queryStatus(Integer rid, Integer uid);

    public List<Route> queryUserAll(Integer uid);

}
