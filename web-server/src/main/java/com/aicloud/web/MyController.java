package com.aicloud.web;

import org.sike.permission.annotation.AiCloudPermission;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;

@Controller
@RequestMapping(value = "/as",consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},headers = {"top"})
public class MyController {


    @RequestMapping(name = "1",value = "/a", method = RequestMethod.GET,headers = {"m=2","mm-0"})
    @AiCloudPermission(name = "t", desc = "sd")
    public String index() {
        return "index";
    }

    @RequestMapping(name = "2",path = "/a1", method = RequestMethod.GET)
    @AiCloudPermission(name = "t1", desc = "sd3")
    public String index1() {
        return "index";
    }

    public static void main(String[] args) {
        String a[] = {"a","b","d"};
        String b[] = {"b","a","c"};
        System.out.println(a.length == b.length && ArrayUtils.containsAll(a,b)&& ArrayUtils.containsAll(b,a));
//        Arrays.sort(a);
//        Arrays.sort(b);
//        System.out.println(Arrays.equals(a,b));
    }
}
