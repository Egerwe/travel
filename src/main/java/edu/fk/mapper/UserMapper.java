package edu.fk.mapper;

import edu.fk.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public User selectByUsername(String username);

    public int insert(User user);

    //支持激活用户的操作
    public int update(User user);

    public User selectByActiveCode(String code);

    //根据用户名和面查询用户
    User selectByUserNameAndPassword(User user);

    List<User> findAll(Map params);

    //删除用户
    int delete(int userid);

    User selectById(Integer uid);
}