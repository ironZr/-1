package cn.zr.mapper;

import cn.zr.pojo.CheckGroup;
import cn.zr.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SetMealMapper {
    void addSetmeal(Setmeal setmeal);

    void addRelation(ArrayList<Map> maps);

    List<Setmeal> findPage(@Param("queryString") String queryString);
}
