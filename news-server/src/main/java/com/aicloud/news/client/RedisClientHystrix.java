package com.aicloud.news.client;

import org.springframework.stereotype.Component;

@Component
public class RedisClientHystrix implements RedisClient{

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public String get(String key) {
        return null;
    }
}
