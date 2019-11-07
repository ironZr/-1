package cn.zr.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.mapper.CheckGroupMapper;
import cn.zr.pojo.CheckGroup;
import cn.zr.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckGroup> list = checkGroupMapper.findPage(queryPageBean.getQueryString());
//        return new PageResult(page.getTotal(), list);  //普通方式
        return PageResult.builder().rows(list).total(page.getTotal()).build(); //链式编程
    }



    @Override
    public void addGroup(CheckGroup checkGroup) {
        checkGroupMapper.addGroup(checkGroup);
        //通过主建回写，得到checkGroup的id
        Integer id = checkGroup.getId();
        //通过得到的id在中间表t_checkgroup_checkitem中添加关系
        List<Integer> checkItems = checkGroup.getCheckitemIds();
        this.groupRelationItems(id, checkItems);
    }

    //建立关系方法
    private void groupRelationItems(Integer id, List<Integer> list) {
        //hutool工具类方法
        if (CollectionUtil.isNotEmpty(list)) {
            //将map这种[20,30]键值对放入list中，然后通过遍历list得到[20,30]这种数据拼在mapper的sql语句中
            ArrayList<Map> maps = new ArrayList<>();
            for (Integer itemId : list) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", id);
                map.put("checkitem_id", itemId);
                maps.add(map);
            }
            checkGroupMapper.setGroupRelationItem(maps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        checkGroupMapper.deleteById(id);
    }

    @Override
    public CheckGroup findById(Integer id) {
        CheckGroup checkGroup = checkGroupMapper.findById(id);
        //根据group的id查询 t_checkgroup_checkitem 中关联的item的id集合
        List<Integer> list = checkGroupMapper.findByIdRealtionItem(id);
        checkGroup.setCheckitemIds(list);
        return checkGroup;
    }

    @Override
    public void eait(CheckGroup checkGroup) {
        //更新检查组的基本信息
        checkGroupMapper.eait(checkGroup);
        //删除原先group和item的关系
        checkGroupMapper.deleteRelation(checkGroup.getId());
        //建立新的关系
        this.groupRelationItems(checkGroup.getId(), checkGroup.getCheckitemIds());
    }

    @Override
    public List<CheckGroup> findAll() {
       return checkGroupMapper.findAll();
    }
}
