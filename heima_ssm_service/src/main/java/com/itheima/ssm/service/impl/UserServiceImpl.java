package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IUserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import com.itheima.ssm.utils.BCryptPasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try{
            userInfo=iUserDao.findByUsername(username);
        }catch(Exception e){
            e.printStackTrace();
        }
      //  User user=new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),getAuthority(userInfo.getRoles()));

        //不再需要｛noop｝了，说明是安全的，密码已经经过加密处理；加了{noop}说明密码没有加密，是以明文的方式使用
        User user=new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        return user;
    }

    public List<SimpleGrantedAuthority> getAuthority(List<Role> roleList) {
        List<SimpleGrantedAuthority> list=new ArrayList<>();
        for(Role role :roleList)
        list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));

        return list;
    }

    @Override
    public List<UserInfo> findAll() throws Exception{
        return iUserDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception{

        userInfo.setPassword(BCryptPasswordEncoderUtils.encodePassword(userInfo.getPassword()));
        iUserDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws  Exception{
        return iUserDao.findById(id);
    }



    @Override
    public List<Role> finOtherRoles(String userid) throws  Exception{
        return iUserDao.findOtherRoles(userid);
    }

    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for(int i=0;i<roleIds.length;i++){
            iUserDao.addRoleToUser(userId,roleIds[i]);
            System.out.println(userId+":"+roleIds[i]);
        }
    }
}
