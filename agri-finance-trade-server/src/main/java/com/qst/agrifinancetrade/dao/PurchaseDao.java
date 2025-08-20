package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Purchase;
import com.qst.agrifinancetrade.model.MyPurchase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PurchaseDao {

    int insertSelective(Purchase record);




    Purchase selectNewPurchaseId(@Param("ownName")String ownName);


    List<MyPurchase> selectByPurchase(String name);
}