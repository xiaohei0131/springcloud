package org.sike.permission.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class PermissionImportBeanDefinitionRegistrer implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 获取 EnableAicloudPermission 的所有属性
//            AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableAiCloudPermission.class.getName(), true));
        // 得到 packages 属性所有的值
//            String[] packages = attributes.getStringArray("packages");

        // 生成 PermissionProcessor 对象，并将所有包含的包传给该对象
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PermissionProcessor.class.getName());
        builder.addPropertyValue("serverUrl", serverUrl());
        builder.addPropertyValue("clientName", clientName());
        // 将 PermissionProcessor 对象注册到 Spring 中
        beanDefinitionRegistry.registerBeanDefinition(PermissionProcessor.class.getName(), builder.getBeanDefinition());
    }


    protected String serverUrl() {
        return this.getEnvironment().getProperty("aicloud.permission.server.url", String.class, "");
    }

    protected String clientName() {
        return this.getEnvironment().getProperty("aicloud.permission.client.name", String.class, "global");
    }

    protected Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
