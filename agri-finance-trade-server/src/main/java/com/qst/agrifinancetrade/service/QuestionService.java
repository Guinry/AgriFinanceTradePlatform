package com.qst.agrifinancetrade.service;

import com.github.pagehelper.PageInfo;
import com.qst.agrifinancetrade.entity.Question;

import java.util.List;

public interface QuestionService {

    void delete(Integer id);

    void insert(Question record);

    Question selectById(Integer id);

    void updateById(Question record);

    List<Question> selectByQuestion(String type);

    PageInfo<Question> selectByKeys(String keys, Integer pageNum);
}
