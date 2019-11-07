package cn.zr.web;

import cn.hutool.core.lang.UUID;
import cn.zr.constant.MessageRedisPhotos;
import cn.zr.entity.PageResult;
import cn.zr.entity.QueryPageBean;
import cn.zr.entity.Result;
import cn.zr.pojo.CheckGroup;
import cn.zr.pojo.Setmeal;
import cn.zr.qiniuyunUtils.QiniuyunUtil;
import cn.zr.service.CheckGroupService;
import cn.zr.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealConroller {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private CheckGroupService checkGroupService;

    @Reference
    private SetmealService setmealService;


    @RequestMapping("/checkGroup")
    public Result checkGroup() {
        List<CheckGroup> list = checkGroupService.findAll();
        return new Result(true, "", list);
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
//目的：使用UUID随机产生文件名称，防止同名文件覆盖
            //得到原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            //截取原来的后缀名
            int index = originalFilename.lastIndexOf('.');
            String photoName = originalFilename.substring(index);   //得到图片的后缀名
            //生成一个随机字符串
            String uuid = UUID.randomUUID().toString();
            //得到新的随机图片名
            String newFileName = uuid + photoName;

            //调用七牛工具类的upload方法进行图片上传
            QiniuyunUtil.upload(imgFile.getBytes(), newFileName);

            //将点击图片上传的数据放入redis中的JEDIS_PHOTOS，为了等下定时进行和数据库的图片进行对比，再而删除脏数据
            jedisPool.getResource().sadd(MessageRedisPhotos.JEDIS_PHOTOS,newFileName);


            return new Result(true, "", newFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "上传图片失败！");
        }
    }

    //添加套餐
    @RequestMapping("/addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal) {
        try {
            setmealService.addSetmeal(setmeal);

            //将数据库中保存的图片的名字记录在redis的DB_PHOTOS中，进而和JEDIS_PHOTOS中的数据进行相差删除！
            jedisPool.getResource().sadd(MessageRedisPhotos.DB_PHOTOS, setmeal.getImg());

            return new Result(true, "添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败！");
        }
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, "", pageResult);
    }

}
