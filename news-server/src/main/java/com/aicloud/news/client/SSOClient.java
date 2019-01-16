package com.aicloud.news.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sso-server",fallback = SSOClientHystrix.class)
@Component("ssoClient")
public interface SSOClient {

    @RequestMapping(method = RequestMethod.GET, value = "/demo/getuser")
    String getuser();
}
