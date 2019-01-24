package com.aicloud.permission.config;

import com.aicloud.permission.annotation.EnableAicloudPermission;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PermissionImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 获取 EnableAicloudPermission 的所有属性
        Map<String, Object> attr = annotationMetadata.getAnnotationAttributes(EnableAicloudPermission.class.getName());
        // 得到 packages 属性所有的值
        String[] packages = (String[]) attr.get("packages");
        List<String> packageList = Arrays.asList(packages);
        // 生成 PermissionProcessor 对象，并将所有包含的包传给该对象
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(PermissionProcessor.class.getName());
        builder.addPropertyValue("packageList", packageList);
        // 将 PermissionProcessor 对象注册到 Spring 中
        beanDefinitionRegistry.registerBeanDefinition(PermissionProcessor.class.getName(), builder.getBeanDefinition());
    }
}
