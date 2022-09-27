package edu.fk.service;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.pojo.Seller;

public interface SellerService {

    PageResult<Seller> queryByCondition(QueryPageBean queryPageBean);

    int delete(int id);

    int update(Seller seller);

    int add(Seller seller);

}