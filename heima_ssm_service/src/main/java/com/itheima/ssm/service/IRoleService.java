package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll() throws Exception;

    void save(Role role)throws Exception;

    Role findById(String id)throws Exception;

    void deleteRole(String id)throws Exception;

    List<Permission> finOtherPermissions(String id)throws Exception;

    void addPermissionTole(String roleid, String[] ids)throws Exception;
}
