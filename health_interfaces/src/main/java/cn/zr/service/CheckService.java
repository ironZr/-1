package cn.zr.service;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.entity.Result;
import cn.zr.exception.CheckItemException;
import cn.zr.pojo.CheckItem;

import java.util.List;


public interface CheckService {
    void addAll(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckItem findByID(Integer id);

    void edit(CheckItem checkItem);

    //使用dubbo的时候抛异常需要在接口定义好 throws CheckItemException
    void deleteByID(Integer id) throws CheckItemException;

    List<CheckItem> findAllItems();
}
