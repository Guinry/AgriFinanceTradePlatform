package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.SellPurchase;

import java.util.List;

public interface SellPurchaseService {

    void add(SellPurchase purchase);

    List<SellPurchase> selectByName();

}
