package com.eaicloud.sso.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="news-server",fallback = NewsClientHystrix.class)
@Component("newsClient")
public interface NewsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/demo/news")
    String getNews();
}
