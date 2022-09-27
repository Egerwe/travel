package edu.fk.mapper;

import edu.fk.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {

    List<Category> queryAll();

    List<Category> selectByCondition(Map param);
    int delete(int id);
    int update(Category category);
    int add(Category category);

}
