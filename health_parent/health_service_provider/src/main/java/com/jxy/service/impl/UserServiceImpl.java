package com.jxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jxy.dao.PermissionDao;
import com.jxy.dao.RoleDao;
import com.jxy.dao.UserDao;
import com.jxy.service.UserService;
import com.pojo.Permission;
import com.pojo.Role;
import com.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
/*
* 用户服务
* */

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    //根据用户名查询数据库获取用户信息和关联的角色信息，以及角色信息关联的权限信息
    public User findByUsername(String username){
        User user = userDao.findByUsername(username); //查询用户基本信息，不包含用户角色
        if(user == null){
            return null;
        }

        Integer userId = user.getId();
        //根据用户Id来查询对应的角色
        Set<Role> roles = roleDao.findByUserId(userId);

        for (Role role : roles) {
            Integer roleId = role.getId();
            //根据角色Id查询关联权限
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            //让角色关联权限
            role.setPermissions(permissions);
        }

        //让用户关联角色
        user.setRoles(roles);
        return user;
    }
}
