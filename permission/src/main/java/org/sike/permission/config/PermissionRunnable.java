package org.sike.permission.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sike.permission.utils.RestTemplateUtil;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class PermissionRunnable implements Runnable {
    private final Log logger = LogFactory.getLog(PermissionRunnable.class);
    String serverUrl;
    MultiValueMap<String, Object> params;

    public PermissionRunnable(String serverUrl, MultiValueMap<String, Object> params) {
        this.serverUrl = serverUrl;
        this.params = params;
    }


    @Override
    public void run() {
        RestTemplate restTemplate = RestTemplateUtil.getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity req = new HttpEntity(params, headers);
        StringBuilder sb = new StringBuilder();
        if (null != serverUrl && serverUrl.endsWith("/")) {
            sb.append(serverUrl.substring(0, serverUrl.length() - 1));
        } else {
            sb.append(serverUrl);
        }
        sb.append("/aicloud.permission.register");
        try {
            ResponseEntity<String> responseEntity = register(restTemplate, sb.toString(), req, 5000, 10);
            if (responseEntity != null) {
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    logger.warn(responseEntity.getBody());
                } else {//发送成功后每隔3分钟再发送一次，兼容服务端重启异常
                    register(restTemplate, sb.toString(), req, 3 * 60000, 10);
                }
            } else {
                logger.error("Unable to report permissions to server [" + serverUrl + "]");
            }
        } catch (Exception e) {
            logger.error("Unable to report permissions to server [" + serverUrl + "]", e);
        }
    }

    private ResponseEntity<String> register(RestTemplate restTemplate, String url, HttpEntity req, long intervalTime, int retryCount) {
        try {
            Thread.sleep(intervalTime);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, req, String.class);
        } catch (RestClientException e) {
            if (retryCount > 0) {
                responseEntity = register(restTemplate, url, req, intervalTime, --retryCount);
            } else {
                throw e;
            }
        }
        return responseEntity;
    }


}
