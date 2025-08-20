package com.qst.agrifinancetrade.service.impl;

import com.qst.agrifinancetrade.dao.ShoppingcartDao;
import com.qst.agrifinancetrade.entity.Shoppingcart;
import com.qst.agrifinancetrade.model.ShoppingModel;
import com.qst.agrifinancetrade.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingcartServiceImpl implements ShoppingcartService {
    @Autowired
    private ShoppingcartDao shoppingcartDao;

    @Override
    public void add(Shoppingcart shoppingcart) {
        shoppingcartDao.insertSelective(shoppingcart);
    }

    @Override
    public void update(Shoppingcart shoppingcart) {
        shoppingcartDao.updateByPrimaryKeySelective(shoppingcart);
    }

    @Override
    public void delete(Integer id) {
        shoppingcartDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<ShoppingModel> selectByUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(name);
        List<ShoppingModel> shoppingcarts = shoppingcartDao.selectByShopping(shoppingcart);
        return shoppingcarts;
    }

    @Override
    public List<ShoppingModel> selectByUserOrderId(Integer orderId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        Shoppingcart shoppingcart = new Shoppingcart();
        shoppingcart.setOwnName(name);
        shoppingcart.setOrderId(orderId);

        List<ShoppingModel> shoppingcarts = shoppingcartDao.selectByShopping(shoppingcart);
        return shoppingcarts;
    }

}
