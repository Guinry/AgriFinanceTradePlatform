package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.Shoppingcart;
import com.qst.agrifinancetrade.model.ShoppingModel;

import java.util.List;

public interface ShoppingcartService {

    void add(Shoppingcart shoppingcart);

    void update(Shoppingcart shoppingcart);

    void delete(Integer id);

    List<ShoppingModel> selectByUsername();

    List<ShoppingModel> selectByUserOrderId(Integer orderId);

}
