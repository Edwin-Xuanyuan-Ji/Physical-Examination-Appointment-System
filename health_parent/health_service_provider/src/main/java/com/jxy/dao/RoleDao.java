package com.jxy.dao;

import com.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(int userId);
}
