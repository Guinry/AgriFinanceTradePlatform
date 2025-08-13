package com.qst.agrifinancetrade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.agrifinancetrade.dao.UserDao;
import com.qst.agrifinancetrade.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDao, User> {
    @Autowired
    private UserDao userDao;

    public List<User> selectAll() {
        return userDao.selectList(null);
    }

    public User selectByUserName(String userName) {
        return userDao.selectByPrimaryKey(userName);
    }

    public void add(User user) {
        userDao.insertSelective(user);
    }

    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    public void delete(String userName) {
        userDao.deleteByPrimaryKey(userName);
    }

    // 修改:实现分页查询方法
    public Page<User> findPage(Integer pageNum) {
        Page<User> page = new Page<>(pageNum, 30);
        return this.page(page);
    }

    // 修改:实现条件分页查询方法
    public Page<User> findPage(User user, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User> queryWrapper = 
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            queryWrapper.like("user_name", user.getUserName());
        }
        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            queryWrapper.like("address", user.getAddress());
        }
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            queryWrapper.eq("role", user.getRole());
        }
        
        return this.page(page, queryWrapper);
    }

    public void loginUpdateByUsername(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    public String selectAddressByName(String ownName) {
        return userDao.selectAddressByName(ownName);
    }
}