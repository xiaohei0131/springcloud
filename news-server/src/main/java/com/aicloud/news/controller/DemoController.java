package com.aicloud.news.controller;

import com.aicloud.news.client.RedisClient;
import com.aicloud.news.client.SSOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    SSOClient ssoClient;

    @Autowired
    RedisClient redisClient;

    @RequestMapping("/news")
    public String getNews() {
        return "Pig love dog!";
    }

    @RequestMapping("/getuser")
    public String getuser() {
        return "message from sso:" + ssoClient.getuser();
    }

    @RequestMapping(value = "/setRedis",method = RequestMethod.POST)
    public boolean setRedis(@RequestParam("key") String key, @RequestParam("value") String value) {
        return redisClient.set(key, value);
    }

    @RequestMapping(value = "/getRedis",method = RequestMethod.GET)
    public Object getRedis(String key) {
        return redisClient.get(key);
    }
}
