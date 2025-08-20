package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.SellPurchase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SellPurchaseDao {

    int insertSelective(SellPurchase record);

    List<SellPurchase> selectByName(@Param("ownName")String ownName);

}