package org.sike.permission.utils;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static RestTemplate restTemplate = null;

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = restTemplate();
        }
        return restTemplate;
    }

    private static RestTemplate restTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true)); // 重试次数
        HttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient); // httpClient连接配置
        clientHttpRequestFactory.setConnectTimeout(20000); // 连接超时
        clientHttpRequestFactory.setReadTimeout(30000); // 数据读取超时时间
        clientHttpRequestFactory.setConnectionRequestTimeout(20000); // 连接不够用的等待时间
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}
