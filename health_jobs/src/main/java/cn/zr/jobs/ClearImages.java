package cn.zr.jobs;

import cn.zr.constant.MessageRedisPhotos;
import cn.zr.qiniuyunUtils.QiniuyunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;


import java.util.Set;

@Component
public class ClearImages {

    @Autowired
    private JedisPool jedisPool;

    public void run() {
        //数据多的key放前面，进行减法
        Set<String> needDeleteImg = jedisPool.getResource().sdiff(MessageRedisPhotos.JEDIS_PHOTOS, MessageRedisPhotos.DB_PHOTOS);


        for (String s : needDeleteImg) {
            //删除七牛上的数据
            QiniuyunUtil.delete(s);
            //删除Redis里面的数据，避免下次再次匹配到
            jedisPool.getResource().srem(MessageRedisPhotos.JEDIS_PHOTOS, s);
        }
    }
}
