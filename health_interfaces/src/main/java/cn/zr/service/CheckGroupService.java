package cn.zr.service;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    PageResult findPage(QueryPageBean queryPageBean);

    void addGroup(CheckGroup checkGroup);

    void deleteById(Integer id);

    CheckGroup findById(Integer id);

    void eait(CheckGroup checkGroup);
}
