package org.sike.permission.config;

import com.alibaba.fastjson.JSONArray;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sike.permission.server.PermissionService;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.net.MalformedURLException;

public class PermissionRunnable implements Runnable {
    private final Log logger = LogFactory.getLog(PermissionRunnable.class);
    String serverUrl;
    String clientName;
    JSONArray permissionList;

    public PermissionRunnable(String serverUrl, String clientName, JSONArray permissionList) {
        this.serverUrl = serverUrl;
        this.clientName = clientName;
        this.permissionList = permissionList;
    }


    @Override
    public void run() {
        try {
            PermissionService permissionService = hessianUserServiceClient();
            permissionService.collectPermission(clientName, permissionList);
        } catch (Exception e) {
            logger.error("Unable to report permissions to server [" + serverUrl + "]", e);
        }
    }

    private PermissionService hessianUserServiceClient() throws MalformedURLException {
        HessianProxyFactory factory = new HessianProxyFactory();
        StringBuilder sb = new StringBuilder();
        if (null != serverUrl && serverUrl.endsWith("/")) {
            sb.append(serverUrl.substring(0, serverUrl.length() - 1));
        } else {
            sb.append(serverUrl);
        }
        sb.append("/permission.service");
        return (PermissionService) factory.create(PermissionService.class,sb.toString());
    }


}
