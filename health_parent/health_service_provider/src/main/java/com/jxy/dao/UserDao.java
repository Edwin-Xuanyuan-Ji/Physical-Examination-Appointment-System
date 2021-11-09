package com.jxy.dao;

import com.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
