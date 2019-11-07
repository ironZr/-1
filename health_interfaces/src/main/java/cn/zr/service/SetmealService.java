package cn.zr.service;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    public void addSetmeal(Setmeal setmeal);


    PageResult findPage(QueryPageBean queryPageBean);
}
