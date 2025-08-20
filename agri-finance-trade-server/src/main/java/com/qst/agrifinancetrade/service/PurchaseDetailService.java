package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.PurchaseDetail;

import java.util.List;

public interface PurchaseDetailService {

    void add(PurchaseDetail purchaseDetail);

    List<PurchaseDetail> selectByPurchaseId(Integer purchaseId);

}
