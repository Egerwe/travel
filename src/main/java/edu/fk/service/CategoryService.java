package edu.fk.service;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryAll();

    PageResult<Category> queryByCondition(QueryPageBean queryPageBean);
    int delete(int id);
    int update(Category category);
    int add(Category category);

}
