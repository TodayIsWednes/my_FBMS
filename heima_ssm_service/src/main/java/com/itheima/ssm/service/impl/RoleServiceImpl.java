package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IRoleDao;
import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao iRoleDao;
    @Override
    public List<Role> findAll() throws Exception {
        return iRoleDao.findAll();
    }

    @Override

    public void save(Role role) throws Exception {
        iRoleDao.save(role);
    }

    @Override
    public Role findById(String id) throws Exception {
        return iRoleDao.findById(id);
    }

    @Override
    public void deleteRole(String id) throws Exception {
        iRoleDao.deleteFromUsers_RoleByRoleId(id);
        iRoleDao.deleteFromRole_PermissionByRoleId(id);
        iRoleDao.deleteByRoleId(id);
    }

    @Override
    public List<Permission> finOtherPermissions(String id) throws Exception {
        return iRoleDao.findOtherPermissions(id);
    }

    @Override
    public void addPermissionTole(String roleid, String[] ids) throws Exception {
        for(int i=0;i<ids.length;i++){
            iRoleDao.addPermissionTole(roleid,ids[i]);
        }
    }
}
