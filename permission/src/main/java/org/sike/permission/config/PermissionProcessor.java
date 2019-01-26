package org.sike.permission.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.sike.permission.annotation.AiCloudPermission;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Lazy
public class PermissionProcessor implements ApplicationContextAware {
    //    private final Log logger = LogFactory.getLog(PermissionProcessor.class);
    private ApplicationContext applicationContext;

    String serverUrl;

    String clientName;

    private static JSONArray permissionList = new JSONArray();
    private static Set<String> permissionSet = new HashSet<>();

    @PostConstruct
    public void postProcessBeanFactory() throws BeansException {
        if (StringUtils.isEmpty(serverUrl)) {
            throw new FatalBeanException("Not found \"aicloud.permission.server.url\".Please check your configuration.");
        }
        // 取得对应Annotation映射，BeanName -- 实例
        Map<String, Object> annotationMap = applicationContext.getBeansWithAnnotation(Controller.class);
        AiCloudPermission permission;
        JSONObject permissionObj;
        Object bean;
        for (Map.Entry<String, Object> ele : annotationMap.entrySet()) {
            bean = ele.getValue();
            for (Method method : bean.getClass().getDeclaredMethods()) {
                permission = method.getAnnotation(AiCloudPermission.class);
                if (permission != null) {
                    if (permissionSet.contains(permission.name())) {
                        throw new BeanInstantiationException(bean.getClass(), "Duplicate permission [" + permission.name() + "]");
                    }
                    permissionSet.add(permission.name());
                    permissionObj = new JSONObject();
                    permissionObj.put("name", permission.name());
                    permissionObj.put("desc", permission.desc());
                    permissionList.add(permissionObj);
                }
            }
        }

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("permission", permissionList);
        params.add("client", clientName);
        PermissionRunnable permissionRunnable = new PermissionRunnable(serverUrl, params);
        new Thread(permissionRunnable).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


}
