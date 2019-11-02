package cn.zr.mapper;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CheckMapper {

    void addAll(CheckItem checkItem);

    List<CheckItem> findPage(@Param("queryString") String queryString);

    CheckItem findById(@Param("id") Integer id);

    void edit(CheckItem checkItem);

    void deleteByID(@Param("id") Integer id);
}
