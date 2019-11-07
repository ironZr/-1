package cn.zr.mapper;

import cn.zr.pojo.OrderSetting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderSettingMapper {

    Integer findByOrderDate(Date orderDate);

    void updateByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(HashMap<String, String> map);

}
