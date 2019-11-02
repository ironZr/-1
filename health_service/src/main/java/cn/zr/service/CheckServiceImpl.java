package cn.zr.service;

import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.mapper.CheckMapper;
import cn.zr.pojo.CheckItem;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckService.class)
@Transactional
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckMapper checkMapper;

    @Override
    public void addAll(CheckItem checkItem) {
         checkMapper.addAll(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件(告诉分页拦截器现在要分页)
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        List<CheckItem> checkItems = checkMapper.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),checkItems);
    }

    @Override
    public CheckItem findByID(Integer id) {
        return checkMapper.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkMapper.edit(checkItem);
    }

    @Override
    public void deleteByID(Integer id) {
        checkMapper.deleteByID(id);
    }
}
