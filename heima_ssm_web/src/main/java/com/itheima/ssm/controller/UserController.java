package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @PostMapping("/save.do")
    public String Save(UserInfo userInfo) throws  Exception{
        iUserService.save(userInfo);
        return "redirect:findAll.do";
    }


    @GetMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {

        ModelAndView mv=new ModelAndView();
        List<UserInfo> list=iUserService.findAll();
        mv.addObject("userList",list);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        UserInfo userInfo=iUserService.findById(id);

        mv.addObject("user",userInfo);

        mv.setViewName("user-show");
        return mv;
    }

    @GetMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id",required=true)String userid) throws Exception {
        //1.根据用户查询用户信息
        UserInfo userInfo=iUserService.findById(userid);
        //2.根据用户id查询可以添加的角色信息
        List<Role> list=iUserService.finOtherRoles(userid);

        ModelAndView mv=new ModelAndView();
        mv.addObject("userInfo",userInfo);
        mv.addObject("roleList",list);
        mv.setViewName("user-role-add");

        return mv;
    }

    //用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name="userId",required = true)String userId,@RequestParam(name="ids",required = true)String[] roleIds){
        iUserService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do";
    }

}
