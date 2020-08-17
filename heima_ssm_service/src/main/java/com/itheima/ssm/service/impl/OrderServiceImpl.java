package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IOrdersDao;
import com.itheima.ssm.domain.Order;
import com.itheima.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrdersDao iOrdersDao;
    @Override
    public List<Order> findAll(int page,int size) throws Exception {
        //参数pageNum是页码，pageSize是每页显示条数
        PageHelper.startPage(page,size);
        return iOrdersDao.findAll();
    }

    @Override
    public Order findById(String orderId) throws Exception {
        return iOrdersDao.findById(orderId);
    }
}
