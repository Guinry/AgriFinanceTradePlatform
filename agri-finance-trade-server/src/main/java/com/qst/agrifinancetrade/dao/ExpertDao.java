package com.qst.agrifinancetrade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.agrifinancetrade.entity.Expert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpertDao extends BaseMapper<Expert> {


    List<Expert> selectAllExpert();

    List<Expert> selectAllByKeys(@Param("keys")String keys);

}
