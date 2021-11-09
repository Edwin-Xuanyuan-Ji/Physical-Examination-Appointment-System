package com.jxy.service;

import com.entity.Result;

import java.util.Map;

public interface OrderService {
    public Result order(Map map) throws Exception;

    public Map findById(int id) throws Exception;
}
