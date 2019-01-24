package com.aicloud.permission;

import com.aicloud.permission.annotation.AiCloudPermission;
import org.springframework.stereotype.Controller;

@Controller
public class DemoController {

    @AiCloudPermission(name = "gold",desc = "大神")
    public void demo(){

    }

    @AiCloudPermission(name = "gold",desc = "大神2")
    public void demo2(){

    }

}
