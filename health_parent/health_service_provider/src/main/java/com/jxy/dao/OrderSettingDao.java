package com.jxy.dao;

import com.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date date);
    public List<OrderSetting> getOrderSettingByMonth(Map<String,String> map);
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    public OrderSetting findByOrderDate(Date orderDate);
}
