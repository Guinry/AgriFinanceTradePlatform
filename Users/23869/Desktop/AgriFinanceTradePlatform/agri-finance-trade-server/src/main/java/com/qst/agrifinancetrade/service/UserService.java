package com.qst.agrifinancetrade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.agrifinancetrade.entity.User;

import java.util.List;

// 修改:UserService应该是一个接口
public interface UserService extends IService<User> {
    List<User> selectAll();

    User selectByUserName(String userName);

    void add(User user);

    void update(User user);

    void delete(String userName);

    // 修改:定义分页查询方法
    Page<User> findPage(Integer pageNum);

    // 修改:定义条件分页查询方法
    Page<User> findPage(User user, Integer pageNum, Integer pageSize);

    void loginUpdateByUsername(User user);

    String selectAddressByName(String ownName);
}