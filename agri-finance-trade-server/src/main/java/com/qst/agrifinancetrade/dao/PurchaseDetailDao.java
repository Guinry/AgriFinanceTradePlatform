package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.PurchaseDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PurchaseDetailDao {

    int insertSelective(PurchaseDetail record);

    List<PurchaseDetail> selectByPurchaseId(@Param("purchaseId")Integer purchaseId);

}