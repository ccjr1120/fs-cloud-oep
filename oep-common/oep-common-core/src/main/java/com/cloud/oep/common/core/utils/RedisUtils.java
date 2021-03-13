package com.cloud.oep.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ccjr
 * 前面几个方法为基本方法
 * 之后的是对他们进行的各个增强方法（如重载）
 */
@Component
public class RedisUtils {
    @Resource(name = "myRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    //这四个为基本操作，set，del，expire

    public boolean set(String key, String value){
        redisTemplate.execute((new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                redisConnection.set(Objects.requireNonNull(serializer.serialize(key)), Objects.requireNonNull(serializer.serialize(value)));
                return true;
            }
        }));
        return true;
    }

    public boolean expire(String key, long time, TimeUnit unit){
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, unit);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void del(String... key){
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //以下为增强方法

    public boolean set(String key, Object object){
        return false;
    }

    public boolean set(String key, String value, long expire){
        return false;
    }
    public boolean set(String key, Object object, long expire){
        return false;
    }

}
