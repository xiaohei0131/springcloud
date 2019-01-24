package com.aicloud.permission.annotation;

import com.aicloud.permission.config.PermissionImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = PermissionImportBeanDefinitionRegistrar.class)
public @interface EnableAicloudPermission {
    String[] packages();
}
