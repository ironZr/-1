package cn.zr.web;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.zr.constant.MessageConstant;
import cn.zr.entity.Result;
import cn.zr.poi.POIUtils;
import cn.zr.pojo.OrderSetting;
import cn.zr.service.OrderSettingService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    //@RequestParam("excelFile") 默认required为true 如果前端上传组件的action中没有excelFile 则失败
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //通过工具类实现把excel中的数据的每个单元格的数据通过集合里面包数组给获取到 集合里为excel的每一行数据
            //相当于 {[2019/11/2,500],[2019/11/4,600],[2019/11/8,500]...} 可能数组长度不止2 等..
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (CollectionUtil.isNotEmpty(list)) {
                //将数据格式化，然后放入集合中保存
                ArrayList<OrderSetting> orderSettings = new ArrayList<>();
                //遍历数组
                for (String[] strings : list) {
                    //如果长度不等于2，则continue
                    if (strings.length != 2) {
                        continue;
                    }
                    DateTime orderTime = null;
                    Integer number = null;
                    //将数据设置到相对应的属性上，如果不是则continue
                    try {
                        orderTime = DateUtil.parse(strings[0], "yyyy/MM/dd");
                        number = Integer.valueOf(strings[1]);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    //
                    OrderSetting orderSetting = new OrderSetting(orderTime, number);
                    orderSettings.add(orderSetting);
                }
                orderSettingService.add(orderSettings);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    //由于接受的为字符串，（基本类型，字符串），名字要一样
    public Result getOrderSettingByMonth(String formatDate) {
        try {
            List list = orderSettingService.getOrderSettingByMonth(formatDate);
            return new Result(true, "", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取数据异常！");
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
