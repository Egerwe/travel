package edu.fk.job;

import java.util.Set;

import edu.fk.constants.RedisConstant;
import edu.fk.util.QiniuUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component //和Service等注解的作用一样
@EnableScheduling //开启定时任务
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    @Scheduled(fixedDelay = 30000) //间隔30秒
    public void clearImg() {
        System.out.println("------------------进入删除无效图片的定时任务------------------------");
        // 1. 根据redis保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> imgSet =
                jedisPool.getResource().sdiff(RedisConstant.ROUTE_IMG_ALL, RedisConstant.ROUTE_IMG_DB);
        if(imgSet != null ) {
            for(String picName : imgSet) {
                // 删除七牛云服务器上无效的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                // 删除redis集合中保存的图片名称
                jedisPool.getResource().srem(RedisConstant.ROUTE_IMG_ALL, picName);
                System.out.println("删除了无效图片-----------------------------------：" + picName);
            }
        }
    }
}