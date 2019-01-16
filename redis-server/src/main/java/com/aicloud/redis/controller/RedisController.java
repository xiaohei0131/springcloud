package com.aicloud.redis.controller;

import com.aicloud.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RedisController {
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "set", method = RequestMethod.POST)
    public boolean set(@RequestParam("key") String key, @RequestParam("value") Object value, @RequestParam(value = "time", defaultValue = "0", required = false) long time) {
        if (time == 0) {
            return redisUtil.set(key, value);
        } else {
            return redisUtil.set(key, value, time);
        }
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object get(@RequestParam("key") String key) {
        return redisUtil.get(key);
    }
}
