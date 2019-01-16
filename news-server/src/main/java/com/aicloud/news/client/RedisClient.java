package com.aicloud.news.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "redis-server", fallback = RedisClientHystrix.class)
@Component("redisClient")
public interface RedisClient {

    @RequestMapping(method = RequestMethod.POST, value = "/set")
    boolean set(@RequestParam("key") String key, @RequestParam("value") Object value);


    @RequestMapping(method = RequestMethod.GET, value = "/get")
    String get(@RequestParam("key")String key);
}
