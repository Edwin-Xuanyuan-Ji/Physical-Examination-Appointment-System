package com.jxy.service.impl;

        import com.alibaba.dubbo.config.annotation.Service;
        import com.jxy.dao.OrderSettingDao;
        import com.jxy.service.OrderSettingService;
        import com.pojo.OrderSetting;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.web.bind.annotation.RequestMapping;

        import java.util.*;

/*
 * 预约设置服务
 * */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    //批量导入预约设置数据
    public void add(List<OrderSetting> orderSettingList) {
        if(orderSettingList != null && orderSettingList.size()>0){
            for(OrderSetting orderSetting : orderSettingList){
                Date orderDate = orderSetting.getOrderDate();
                long count = orderSettingDao.findCountByOrderDate(orderDate);
                //判断当前日期是否已经被预约
                if(count>0){
                    //已经进行了预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //没有进行预约设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
    //根据月份查询对应的预约设置数据
    public List<Map> getOrderSettingByMonth(String date) { //格式:yyyy-MM
        String begin = date + "-1";
        String end = date + "-31";
        Map<String,String> map = new HashMap<String, String>();
        map.put("begin",begin);
        map.put("end",end);

        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> res = new ArrayList<Map>();
        if(list != null && list.size()>0){
            for(OrderSetting orderSetting:list){
                Map<String,Object> m = new HashMap<String, Object>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                res.add(m);
            }
        }
        return res;
    }

    //根据日期修改预约设置
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        //判断当前日期是否已经被预约
        if(count>0){
            //已经进行了预约设置，执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //没有进行预约设置，执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }
}
