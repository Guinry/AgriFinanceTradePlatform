package com.qst.agrifinancetrade.service.impl;

import com.qst.agrifinancetrade.dao.PurchaseDetailDao;
import com.qst.agrifinancetrade.entity.PurchaseDetail;
import com.qst.agrifinancetrade.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    @Autowired
    private PurchaseDetailDao purchaseDetailDao;

    @Override
    public void add(PurchaseDetail purchaseDetail) {
        purchaseDetailDao.insertSelective(purchaseDetail);
    }

    @Override
    public List<PurchaseDetail> selectByPurchaseId(Integer purchaseId) {
        List<PurchaseDetail> purchaseDetails = purchaseDetailDao.selectByPurchaseId(purchaseId);
        return purchaseDetails;
    }

}
