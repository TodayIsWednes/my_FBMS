package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @PostMapping("/save.do")
    public String  save(Role role) throws Exception {

        iRoleService.save(role);
        return "redirect:findAll.do";
    }
    @RequestMapping("/findAll.do")
    public ModelAndView findAll()throws Exception{
        ModelAndView mv=new ModelAndView();
        List<Role> list=iRoleService.findAll();
        mv.addObject("roleList",list);
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv=new ModelAndView();
        Role role =iRoleService.findById(id);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }

    @RequestMapping("/deleteRole.do")
    public String deleteRole(String id)throws Exception{
        iRoleService.deleteRole(id);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam("id")String id) throws Exception {
        //根据roleID查询role
        Role role=iRoleService.findById(id);
        //根据roldId查询可以添加的权限
        List<Permission> list=iRoleService.finOtherPermissions(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("role",role);
        mv.addObject("permissionList",list);
        mv.setViewName("role-permission-add");
        return mv;
    }

    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name="roleId",required = true)String roleid,@RequestParam(name="",required = true)String[] ids) throws Exception {
        iRoleService.addPermissionTole(roleid,ids);
        return "redirect:findAll.do";
    }

}
