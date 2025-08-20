package com.qst.agrifinancetrade.service.impl;

import com.qst.agrifinancetrade.dao.BankDao;
import com.qst.agrifinancetrade.entity.Bank;
import com.qst.agrifinancetrade.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankDao bankDao;

    @Override
    public List<Bank> selectAllBank() {

        List<Bank> banks = bankDao.selectAllBank();

        return banks;
    }

}
