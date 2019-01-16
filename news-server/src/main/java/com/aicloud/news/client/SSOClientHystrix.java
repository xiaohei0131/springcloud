package com.aicloud.news.client;

import org.springframework.stereotype.Component;

@Component
public class SSOClientHystrix implements SSOClient {
    @Override
    public String getuser() {
        return "SSO 异常";
    }
}
