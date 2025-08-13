package com.qst.agrifinancetrade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.agrifinancetrade.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressDao extends BaseMapper<Address> {
    // 保留自定义方法，但可以通过继承BaseMapper使用标准CRUD操作
    int insertSelective(Address address);

    List<Address> selectByExample(@Param("name")String name);

    Address selectOneByExample(String name,String isDef);

    int updateByPrimaryKey(Address address);

    Address selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);
}