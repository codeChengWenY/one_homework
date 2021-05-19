package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;


    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;


    // 删除
    public  Integer delete(User user);


    // 保存
    public  Integer save(User user);

    // 更新
    public  Integer update(User user);

}
