package com.qst.agrifinancetrade.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.agrifinancetrade.dao.AddressDao;
import com.qst.agrifinancetrade.dao.UserDao;
import com.qst.agrifinancetrade.entity.User;
import com.qst.agrifinancetrade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressDao addressDao;
    //每页显示多条数据
    private  Integer pageSize = 30;

    @Override
    public List<User> selectAll() {
        return userDao.selectList(null);
    }

    @Override
    public void add(User user) {
//        加个默认地址
//        String userName = user.getUserName();
//        Address address = new Address();
//        address.setOwnName(userName);
//        address.setId(1);
//        address.setIsDefault(true);
//        address.setConsignee("请输入收货人姓名...");
//        address.setPhone("请输入收货人手机号...");
//        address.setAddressDetail("请输入收货人详细地址信息...");
//        addressDao.insertSelective(address);
        String password = user.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userDao.insertSelective(user);
    }

    @Override
    public void update(User user) {
        String password = user.getPassword();
        if (password!=null){
            String encode = passwordEncoder.encode(password);
            user.setPassword(encode);
        }
        user.setUpdateTime(new Date());
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String userName) {
        userDao.deleteByPrimaryKey(userName);
    }

    @Override
    public User selectByUserName(String userName) {
        User user = userDao.selectByPrimaryKey(userName);
//        System.out.println("====================================loadUserByUsername访问了数据库===================================");
        return user;
    }

    // 修改:使用MyBatis-Plus分页功能
    @Override
    public Page<User> findPage(Integer pageNum) {
        Page<User> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }

    // 修改:使用MyBatis-Plus分页和查询功能
    @Override
    public Page<User> findPage(User user, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        
        if (!StringUtils.isEmpty(user.getUserName())) {
            queryWrapper.like("user_name", user.getUserName());
        }
        if (!StringUtils.isEmpty(user.getAddress())){
            queryWrapper.like("address", user.getAddress());
        }
        if (!StringUtils.isEmpty(user.getRole())){
            queryWrapper.eq("role", user.getRole());
        }
        
        return this.page(page, queryWrapper);
    }

    @Override
    public void loginUpdateByUsername(User user) {
        user.setUpdateTime(new Date());
        userDao.updateByPrimaryKeySelective(user);
    }
}