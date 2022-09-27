package edu.fk.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fk.mapper.SellerMapper;
import edu.fk.pojo.Seller;
import edu.fk.service.SellerService;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private  SellerMapper sellerMapper;

    public PageResult<Seller> queryByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Map param = new HashMap();
        param.put("sname", queryPageBean.getQueryString());
        Page<Seller> routesPage = (Page<Seller>) sellerMapper.selectByCondition(param);

        return new PageResult<Seller>(routesPage.getTotal(),routesPage.getResult());
    }

    public int delete(int id) {
        return sellerMapper.delete(id);
    }
    public int update(Seller seller){
        return sellerMapper.update(seller);
    }

    public int add(Seller seller) {
        return  sellerMapper.add(seller);
    }

}
