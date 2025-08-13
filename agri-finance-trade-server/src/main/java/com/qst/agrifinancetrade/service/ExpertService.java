package com.qst.agrifinancetrade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.agrifinancetrade.entity.Expert;

import java.util.List;

public interface ExpertService {

    void delete(String type);

    void insert(Expert record);

    Expert selectById(String type);

    int updateById(Expert record);

    List<Expert> selectAllExpert();

    Page<Expert> findPage(Integer pageNum);

    Page<Expert> findPageByKeys(String keys, Integer pageNum);

}
