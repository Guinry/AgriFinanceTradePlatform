package com.qst.agrifinancetrade.dao;

import com.qst.agrifinancetrade.entity.Shoppingcart;
import com.qst.agrifinancetrade.model.ShoppingModel;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public interface ShoppingcartDao {

    int deleteByPrimaryKey(Integer shoppingId);

    int insertSelective(Shoppingcart shoppingcart);

    List<ShoppingModel> selectByShopping(Shoppingcart shoppingcart);

    int updateByPrimaryKeySelective(Shoppingcart shoppingcart);

}