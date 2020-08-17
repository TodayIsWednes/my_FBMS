package com.itheima.ssm.dao;


import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface IUserDao {
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property="username",column="username"),
            @Result(property="email",column="email"),
            @Result(property="password",column="password"),
            @Result(property="phoneNum",column="phoneNum"),
            @Result(property="status",column="status"),
            @Result(property="roles",column="id",javaType =java.util.List.class,many=@Many(select="com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))
    }


    )
    UserInfo findByUsername(String username);

    @Select("select * from users")
    List<UserInfo> findAll() ;

    @Select("insert into users(id,email,username,password,phoneNum,status) values(uuid(),#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    @Select("select * from users where id=#{id}")
    @Results(value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "email", property = "email"),
            @Result(column = "username", property = "username"),
            @Result(column = "phoneNum", property = "phoneNum"),
            @Result(column = "status", property = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class, many = @Many(select="com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findById(String id);

    @Select("select * from role where id not in (select roleId from users_role where userId=#{userid})")
    List<Role> findOtherRoles(String userid);


    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId")String userId, @Param("roleId")String roleId);
}
