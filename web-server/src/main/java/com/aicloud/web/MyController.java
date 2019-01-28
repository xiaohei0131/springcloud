package com.aicloud.web;

import org.sike.permission.annotation.AiCloudPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class MyController {


    @RequestMapping("/")
    @AiCloudPermission(name = "t", desc = "sd")
    public String index() {
        return "index";
    }

    @RequestMapping("/1")
    @AiCloudPermission(name = "t1", desc = "sd3")
    public String index1() {
        return "index";
    }
}
