package com.aicloud.web;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

public class RouterUtil {
    private static RestTemplate restTemplate = new RestTemplate();
    private static RequestUtil requestUtil = new RequestUtil();
    private static ResponseUtil responseUtil = new ResponseUtil();

    private String routerUrl(String url){
        StringBuilder realUrl = new StringBuilder();
        if(url.startsWith("/sso/")){
            realUrl.append("http://10.0.203.154");
            realUrl.append(url.substring(url.indexOf("/sso/")+1));
        }
        return realUrl.toString();
    }

    public void send(HttpServletRequest req, HttpServletResponse resp) throws IOException, URISyntaxException {
        RequestEntity<byte[]> requestEntity = requestUtil.init(req,routerUrl(req.getRequestURI()));
        ResponseEntity responseEntity = restTemplate.exchange(requestEntity,byte[].class);
        responseUtil.resolveResp(resp,responseEntity);
    }

}
