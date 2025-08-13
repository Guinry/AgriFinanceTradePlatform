package com.qst.agrifinancetrade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.agrifinancetrade.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends BaseMapper<User> {

    List<User> selectAll();

    int insertSelective(User user);
    
    List<User> selectByExample(@Param("name") String name);
    
    User selectByPrimaryKey(String userName);
    
    int updateByPrimaryKeySelective(User user);
    
    int deleteByPrimaryKey(String userName);
    
    String selectAddressByName(@Param("ownName") String ownName);
}
