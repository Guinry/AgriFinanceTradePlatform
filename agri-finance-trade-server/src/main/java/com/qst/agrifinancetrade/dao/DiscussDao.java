package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Discuss;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiscussDao {

    int insertSelective(Discuss record);

    List<Discuss> selectByKnowledgeId(@Param("knowledgeId") Integer knowledgeId);

}