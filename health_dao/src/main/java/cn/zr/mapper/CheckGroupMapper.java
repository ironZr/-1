package cn.zr.mapper;

import cn.zr.entity.PageResult;
import cn.zr.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {

    List<CheckGroup> findPage(@Param("queryString") String queryString);

    void addGroup(CheckGroup checkGroup);

    void deleteById(@Param("id") Integer id);

    CheckGroup findById(@Param("id") Integer id);

    void eait(CheckGroup checkGroup);

    void setGroupRelationItem(ArrayList<Map> maps);

    List<Integer> findByIdRealtionItem(@Param("id") Integer id);

    void deleteRelation(@Param("id") Integer id);

    List<CheckGroup> findAll();

}
