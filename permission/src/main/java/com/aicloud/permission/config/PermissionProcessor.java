package com.aicloud.permission.config;

import com.aicloud.permission.annotation.AiCloudPermission;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.*;

public class PermissionProcessor implements BeanPostProcessor {
    List<String> packageList;

    private static List<Map<String, Object>> permissionList = new ArrayList<>();
    private static Set<String> permissionSet = new HashSet<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 遍历 LogImportBeanDefinitionRegistrar 传过来的所有包名

        AiCloudPermission permission;
        Map<String, Object> permissionMap;
        for (String packageName : packageList) {
            if (bean.getClass().getName().startsWith(packageName)) {
                for (Method method : bean.getClass().getDeclaredMethods()) {
                    permission = method.getAnnotation(AiCloudPermission.class);
                    if (permission != null) {
                        if (permissionSet.contains(permission.name())) {
                            throw new BeanInstantiationException(bean.getClass(), "Duplicate permission [" + permission.name() + "]");
                        }
                        permissionSet.add(permission.name());
                        permissionMap = new HashMap<>();
                        permissionMap.put("name", permission.name());
                        permissionMap.put("desc", permission.desc());
                        permissionList.add(permissionMap);
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public List<String> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<String> packageList) {
        this.packageList = packageList;
    }
}
