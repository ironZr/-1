package cn.zr.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.mapper.SetMealMapper;
import cn.zr.pojo.Setmeal;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void addSetmeal(Setmeal setmeal) {
        //添加套餐的信息
        setMealMapper.addSetmeal(setmeal);
        //添加套餐中关联的检查组到中间表和套餐建议联系
        Integer id = setmeal.getId();
        addSetmealRelationGroup(setmeal.getCheckgroupIds(), id);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> list = setMealMapper.findPage(queryPageBean.getQueryString());
        return PageResult.builder().total(page.getTotal()).rows(list).build();
    }

    public void addSetmealRelationGroup(List<Integer> list, Integer id) {
        if (CollectionUtil.isNotEmpty(list)) {
            ArrayList<Map> maps = new ArrayList<>();
            for (Integer integer : list) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("stemeal_id", id);
                map.put("checkgroup_id", integer);
                maps.add(map);
            }
            setMealMapper.addRelation(maps);
        }
    }
}
