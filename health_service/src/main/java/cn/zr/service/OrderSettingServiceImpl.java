package cn.zr.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.zr.mapper.OrderSettingMapper;
import cn.zr.pojo.OrderSetting;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    //遍历集合，实现添加
    public void add(ArrayList<OrderSetting> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            for (OrderSetting orderSetting : list) {
                Integer count = orderSettingMapper.findByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    orderSettingMapper.updateByOrderDate(orderSetting);
                } else {
                    orderSettingMapper.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List getOrderSettingByMonth(String date) {
        //查询当月数据，sql可以用模糊查询，但是效率低，所以用拼接方法得出一个月的范围，进行查询
        String dateFirst = date + "-1";
        String dateLast = date + "-31";
        //将数据放入map中，方便数据库查询
        HashMap<String, String> map = new HashMap<>();
        map.put("dateFirst", dateFirst);
        map.put("dateLast", dateLast);
        List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);
        //将得到的数据进行封装，已变前端进行展示
        ArrayList<Object> orderList = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            HashMap<String, Object> orderMap = new HashMap<>();
            orderMap.put("date", orderSetting.getOrderDate().getDate());
            orderMap.put("month", orderSetting.getOrderDate().getMonth());
            orderMap.put("number", orderSetting.getNumber());
            orderMap.put("reservations", orderSetting.getReservations());
            orderList.add(orderMap);
        }
        return orderList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //根据参数的时间判断是否数据库有此数据，如果有的话则修改。没有的话则添加
        Integer count = orderSettingMapper.findByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            orderSettingMapper.updateByOrderDate(orderSetting);
        } else {
            orderSettingMapper.add(orderSetting);
        }
    }
}
