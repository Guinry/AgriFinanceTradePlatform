package com.qst.agrifinancetrade.service.impl;

import com.qst.agrifinancetrade.dao.DiscussDao;
import com.qst.agrifinancetrade.entity.Discuss;
import com.qst.agrifinancetrade.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussServiceImpl implements DiscussService {
    @Autowired
    private DiscussDao discussDao;

    @Override
    public void add(Discuss discuss) {
        discussDao.insertSelective(discuss);
    }

    @Override
    public List<Discuss> selectByKnowledgeId(Integer knowledgeId) {
        List<Discuss> Discuss = discussDao.selectByKnowledgeId(knowledgeId);
        return Discuss;
    }

}
