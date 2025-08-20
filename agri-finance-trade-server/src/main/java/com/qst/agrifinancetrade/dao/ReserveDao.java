package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Reserve;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReserveDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Reserve record);

    Reserve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reserve record);

    List<Reserve> selectByReserve(Reserve record);
}