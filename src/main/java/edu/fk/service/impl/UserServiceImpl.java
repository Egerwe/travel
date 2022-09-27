package edu.fk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.fk.entity.PageResult;
import edu.fk.entity.QueryPageBean;
import edu.fk.mapper.UserMapper;
import edu.fk.pojo.User;
import edu.fk.service.UserService;
import edu.fk.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service //注册自己为一个Service
@Transactional //事务支持
public class UserServiceImpl implements UserService {
    //自动注入mapper对象
    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public int resister(User user) throws Exception {
        //1、做用户名重复的校验
        User user1 = checkUsername(user.getUsername());
        if(user1 !=null){
            throw new RuntimeException("用户已经存在！");
        }
        //2、密码的加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));

        //生成邮件激活的code
        String activeCode = UUID.randomUUID().toString();
        user.setCode(activeCode);//保存激活码，收到邮件后，点击激活
        user.setStatus("N");//未激活

        //插入数据
        int ret = userMapper.insert(user);

        //3、发送激活邮件
        String content = "<a href='http://localhost/user/active.do?code='"+ activeCode +">点击激活【融云旅游网】</a>";
        //MailUtils.sendMail(user.getEmail(), content, "融云旅游网激活邮件");

        return ret;
    }

    /**
     * 插入用户数据
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int add(User user) throws Exception {
        //检查用户名是否重复
        User user1 = checkUsername(user.getUsername());
        if(user1 !=null){
            throw new RuntimeException("用户已经存在！");
        }
        //密码加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));

        int ret =userMapper.insert(user);

        return ret;
    }

    //返回处理结果： -1 激活失败 ； 1 激活成功
    @Override
    public int activeUser(String code){
        //1、根据code查询用户
        User user = userMapper.selectByActiveCode(code);
        if(user == null)
            return -1;

        //2、更新用户状态
        user.setStatus("Y");
        userMapper.update(user);

        return 1;
    }

    @Override
    public User login(User user){
        //1、查询用户
        try {
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        } catch (Exception e){
            e.printStackTrace();
        }
        //2、返回查询结果
        User user1 = userMapper.selectByUserNameAndPassword(user);
        return user1;
    }

    @Override
    public PageResult findAll(QueryPageBean pageBean){
        //翻页的处理 --- 由翻页插件pageHelper实现
        PageHelper.startPage(pageBean.getCurrentPage(),pageBean.getPageSize());

        //实现返回的是封装后的page对象
//        Page<User> users = (Page<User>) userMapper.findAll();
        Map param = new HashMap();
        param.put("queryString",pageBean.getQueryString());

        Page<User> users = (Page<User>) userMapper.findAll(param);

        return new PageResult(users.getTotal(),users.getResult());
    }

    @Override
    public int delete(int userid) {
        return userMapper.delete(userid);
    }

    @Override
    public int update(User user) {
        //只更新需要更新的字段，不会全部更新：
        // 1）查询最新的数据
        User user1 = userMapper.selectById(user.getUid());
        // 2）更新对应的字段的数据
        try {
            user1.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
        }
        user1.setName(user.getName());
        user1.setBirthday(user.getBirthday());
        user1.setSex(user.getSex());
        user1.setEmail(user.getEmail());
        user1.setTelephone(user.getTelephone());
        user1.setStatus(user.getStatus());

        // 3）提交到数据库
        return userMapper.update(user1);
    }
}
