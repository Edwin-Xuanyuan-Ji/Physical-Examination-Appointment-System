package com.jxy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.constant.MessageConstant;
import com.entity.Result;
import com.jxy.dao.MemberDao;
import com.jxy.dao.OrderDao;
import com.jxy.dao.OrderSettingDao;
import com.jxy.service.OrderService;
import com.pojo.Member;
import com.pojo.Order;
import com.pojo.OrderSetting;
import com.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
        if(orderSetting == null){
            //指定日期没有进行预约设置，无法预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();//可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if(reservations >= number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String telephone =(String) map.get("telephone"); //获取页面输入的手机号
        Member member = memberDao.findByTelephone(telephone);

        //4、检查当前用户是否为会员，如果不是会员则自动完成注册并进行预约
        if(member != null){
            //判断是否重复预约
            Integer id = member.getId(); //会员id
            Date order_date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//预约套餐id
            Order order = new Order(id,order_date,Integer.parseInt(setmealId));
            //根据条件进行查询
            List<Order> list = orderDao.findByCondition(order);
            if(list != null && list.size()>0){
                //重复预约，无法完成预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        //5、预约成功，更新当日的已预约人数
        //保存预约信息到预约表
        Order order = new Order(member.getId(),
                DateUtils.parseString2Date(orderDate),
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));

        orderDao.add(order);
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //根据预约id查询预约相关信息(体检人姓名、预约日期、套餐名称、预约类型)
    public Map findById(int id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

}
