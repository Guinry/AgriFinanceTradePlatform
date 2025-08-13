package com.qst.agrifinancetrade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.agrifinancetrade.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends BaseMapper<Order> {

}
