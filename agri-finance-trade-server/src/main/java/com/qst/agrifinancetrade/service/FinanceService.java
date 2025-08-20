package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.*;

import java.util.List;

public interface FinanceService {

    void add(Finance finance);

    List<Finance> selectByFinance(Finance finance);

    Finance selectByFinanceId(Integer id);

    void deleteByFinanceId(Integer id);

    void updateByFinance(Finance finance);

    List<Intention> selectIntentionByName(String name);

    void updateIntentionByName(Intention intention);

    void insertIntentionByName(Intention intention);

    void deleteIntentionByName(String name);

    List<Recommend> selectRecommend(String name);

    void AuthorizationtoUser(String name, String userName);

    FinanceUserDetails selectFinanceUser(String name,String bankId);

    boolean selectIfApply(String name);


    void addMulti(Finance finance);
}
