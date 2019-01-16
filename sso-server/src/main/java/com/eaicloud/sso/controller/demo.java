package com.eaicloud.sso.controller;

import com.eaicloud.sso.client.NewsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class demo {

    @Autowired
    NewsClient newsClient;

    @RequestMapping("/sso")
    public String index(){
        return "I am sso-server";
    }
    @RequestMapping("/getuser")
    public String getuser(){
        return "Hello, sso user";
    }

    @RequestMapping("/getnews")
    public String getnews(){
        return "message from news:"+newsClient.getNews();
    }
}
