package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    //产品添加
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.save(product);
        return "redirect:findAll.do";
    }
    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView finAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Product> list=productService.findAll();
        mv.addObject("productList",list);
        mv.setViewName("product-list1");
        return mv;
    }
}
