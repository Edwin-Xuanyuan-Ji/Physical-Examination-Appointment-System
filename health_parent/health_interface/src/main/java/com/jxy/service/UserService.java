package com.jxy.service;

import com.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
