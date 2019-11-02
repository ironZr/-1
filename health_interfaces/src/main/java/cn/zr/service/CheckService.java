package cn.zr.service;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.entity.Result;
import cn.zr.pojo.CheckItem;


public interface CheckService {
    void addAll(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckItem findByID(Integer id);

    void edit(CheckItem checkItem);

    void deleteByID(Integer id);
}
