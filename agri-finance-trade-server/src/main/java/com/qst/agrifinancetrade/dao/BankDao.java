package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Bank;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankDao {

    List<Bank> selectAllBank();

}