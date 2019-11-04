package cn.zr.web;

import cn.zr.constant.MessageConstant;
import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.entity.Result;
import cn.zr.exception.CheckItemException;
import cn.zr.pojo.CheckItem;
import cn.zr.service.CheckService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/check")
public class CheckController {

    @Reference
    private CheckService checkService;


    //添加检查项
    @RequestMapping("/add")
    public Result addAll(@RequestBody CheckItem checkItem) {
        try {
            checkService.addAll(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        //前端是传的对象，所以只能转换为json接收，如果是键值对，则可以不用@RequestBody
        PageResult pageResult = checkService.findPage(queryPageBean);
        return pageResult;
    }


    //回写
    @RequestMapping("/findById")
    public Result findBuId(Integer id) {
        try {
            CheckItem checkItem=checkService.findByID(id);
            return new Result(true, "请检查数据", checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "数据有误，请联系管理员！");
        }
    }


    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkService.edit(checkItem);
            return new Result(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "数据有误，请联系管理员！");
        }
    }

    //删除
    @RequestMapping("/deleteByID")
    public Result deleteByID(Integer id) {
        try {
            checkService.deleteByID(id);
        }catch (CheckItemException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
        return new Result(true, "删除成功！");
    }


    @RequestMapping("/findAllItems")
    public Result findAllItems() {
        try {
            List<CheckItem> list = checkService.findAllItems();
            return new Result(true, "", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "请刷新页面");
        }
    }




}
