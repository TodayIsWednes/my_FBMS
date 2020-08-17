package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Order;
import com.itheima.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    IOrderService iOrderService;
    //获取所有的订单信息 未分页
    /*
    @RequestMapping("/findAll.do")
    public ModelAndView findAll () throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Order> ls=iOrderService.findAll();
        System.out.println(ls);
        mv.addObject("ordersList",ls);
        mv.setViewName("orders-list");
        return mv;
    }*/

    @RequestMapping("/findAll.do")
    public ModelAndView findAll (@RequestParam(name="page",required=true,defaultValue="1")int page, @RequestParam(name="size",required=true,defaultValue ="4")int size) throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Order> ol=iOrderService.findAll(page,size);
        //PageInfo 就是一个分页bean
        PageInfo pageInfo=new PageInfo(ol);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name="id",required=true)String orderId) throws Exception {
        ModelAndView mv=new ModelAndView();
        Order orders=iOrderService.findById(orderId);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }


}
