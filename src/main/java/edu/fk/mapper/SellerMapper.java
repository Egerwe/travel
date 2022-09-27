package edu.fk.mapper;

import java.util.List;
import java.util.Map;

import edu.fk.pojo.Seller;

public interface SellerMapper {

    List<Seller> selectByCondition(Map param);

    int delete(int id);

    int update(Seller seller);

    int add(Seller seller);

}
