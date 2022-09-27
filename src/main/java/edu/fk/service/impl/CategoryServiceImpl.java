package edu.fk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fk.constants.RedisConstant;
import edu.fk.mapper.CategoryMapper;
import edu.fk.pojo.Category;
import edu.fk.service.CategoryService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service //标注是一个service
@Transactional //启动事物
public class CategoryServiceImpl implements CategoryService {
    //自动注入：spring容器根据需要注入的类型（CategoryMapper），将容器中符合的注入到属性里
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public List<Category> queryAll() {
        Jedis jedis=jedisPool.getResource();
        //缓存的处理:查询缓存
        String catStr = jedis.get(RedisConstant.REDIS_CATEGORY);
        if (catStr !=null){
            System.out.println("从缓存中获取到了数据....casStr...."+catStr);
            //使用JSON解析，获取分类列表
            List<Category> categories = JSON.parseArray(catStr,Category.class);
            return  categories;
        }else {
            //查询数据
            List<Category> categories = categoryMapper.queryAll();

            //缓存数据：如果查询到，将数据放到缓存
            jedis.set(RedisConstant.REDIS_CATEGORY,JSON.toJSONString(categories));

            return  categories;
        }
    }
    public PageResult<Category> queryByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Map param = new HashMap();
        param.put("cname", queryPageBean.getQueryString());
        Page<Category> categoryPage = (Page<Category>) categoryMapper.selectByCondition(param);


        return new PageResult<Category>(categoryPage.getTotal(),categoryPage.getResult());
    }

    public int delete(int id) {
        return categoryMapper.delete(id);
    }

    public int update(Category category) {
        return categoryMapper.update(category);
    }

    public int add(Category category) {
        return  categoryMapper.add(category);
    }
}
