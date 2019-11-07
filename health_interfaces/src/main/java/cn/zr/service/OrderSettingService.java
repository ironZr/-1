package cn.zr.service;

import cn.zr.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;

public interface OrderSettingService {

    void add(ArrayList<OrderSetting> list);

    List getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
