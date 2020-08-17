package com.itheima.ssm.service;

import com.itheima.ssm.domain.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll(int page,int size)throws Exception;

    Order findById(String orderId)throws Exception;
}
