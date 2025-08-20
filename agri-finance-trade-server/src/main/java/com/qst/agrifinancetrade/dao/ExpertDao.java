package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Expert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpertDao {

    int deleteByPrimaryKey(String userName);

    int insertSelective(Expert record);

    Expert selectByPrimaryKey(String userName);

    int updateByPrimaryKeySelective(Expert record);

    List<Expert> selectAllExpert();

    List<Expert> selectAllByKeys(@Param("keys")String keys);

}