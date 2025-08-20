package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.Discuss;

import java.util.List;

public interface DiscussService {

    void add(Discuss discuss);

    List<Discuss> selectByKnowledgeId(Integer knowledgeId);

}
