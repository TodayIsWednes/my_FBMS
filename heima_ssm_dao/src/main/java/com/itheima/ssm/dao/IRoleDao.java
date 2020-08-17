package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property = "roleName",column="roleName"),
            @Result(property="roleDesc",column="roleDesc"),
            @Result(property="permissions",column="id",javaType = java.util.List.class,many=@Many(select="com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId")),
    })
    List<Role> findRoleByUserId(String userId)throws Exception;

    @Select("select * from role where id in (select roleId from role_permission where permissionId=#{permissionId})")
    List<Role> findRoleByPermissionId(String permissionId)throws Exception;

    @Select("select * from role")
    List<Role> findAll()throws Exception;

    @Insert("insert into role(id,roleName,roleDesc) values(uuid(),#{roleName},#{roleDesc})")
    void save(Role role)throws Exception;

    @Select("select * from role where id=#{id}")
    @Results( {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions",column="id",javaType = java.util.List.class,many=@Many(select="com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    Role findById(String id)throws Exception;

    @Delete("delete from users_role where roleId=#{roleId}")
    void deleteFromUsers_RoleByRoleId(String roleId);

    @Delete("delete from role_permission where roleId=#{roleId}")
    void deleteFromRole_PermissionByRoleId(String roleId);

    @Delete("delete from role where id=#{roleId}")
    void deleteByRoleId(String roleId);

    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{id})")
    List<Permission> findOtherPermissions(String id);

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionTole(@Param("roleId")String roleid, @Param("permissionId")String id);
}
