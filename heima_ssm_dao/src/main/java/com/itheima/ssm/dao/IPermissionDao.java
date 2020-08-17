package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {
    @Select("select * from permission where id in(select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findPermissionByRoleId(String id) throws Exception;

    @Select("select * from permission")
    List<Permission> findAll();

    @Select("insert into permission(id,permissionName,url) values(uuid(),#{permissionName},#{url})")
    void save(Permission permission);

    @Select("select * from permission where id=#{id}")
    @Results({
            @Result(id=true,property = "id",column="id"),
            @Result(property = "permissionName",column="permissionName"),
            @Result(property = "url",column = "url"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many=@Many(select="com.itheima.ssm.dao.IRoleDao.findRoleByPermissionId"))
    })
    Permission findById(String id);
}
