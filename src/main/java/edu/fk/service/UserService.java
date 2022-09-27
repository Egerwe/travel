package edu.fk.service;

import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.pojo.User;

public interface UserService {

    User checkUsername(String username);

    int resister(User user) throws Exception;

    int add(User user) throws Exception;
    //激活用户功能
    int activeUser(String code);

    User login(User user);

    PageResult findAll(QueryPageBean pageBean);

    //删除用户
    int delete(int userid);

    //更新用户
    int update(User user);
}
