package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    List<Question> selectByQuestion(Question record);

    List<Question> selectAllByKeys(@Param("keys")String keys);

}