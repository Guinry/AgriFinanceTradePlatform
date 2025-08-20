package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.Purchase;
import com.qst.agrifinancetrade.model.MyPurchase;

import java.util.List;

public interface PurchaseService {

    void add(Purchase purchase);

    List<MyPurchase> selectByPurchaseType();

    Purchase selectNewPurchaseId(String ownName);

}
