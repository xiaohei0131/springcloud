package com.eaicloud.sso.client;

import org.springframework.stereotype.Component;

@Component
public class NewsClientHystrix implements NewsClient {
    @Override
    public String getNews() {
        return "新闻服务不可用";
    }
}
