package edu.fk.mapper;

import java.util.List;

import edu.fk.pojo.Favorite;
import edu.fk.pojo.Route;

public interface FavoriteMapper {

    public void insert(Favorite favorite);

    public void delete(Favorite favorite);

    public Favorite selectByRidAndUid(Favorite favorite);

    public List<Route> selectByUserId(Integer uid);

    public void deleteByUid(Integer uid);

}
