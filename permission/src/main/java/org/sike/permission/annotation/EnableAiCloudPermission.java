package org.sike.permission.annotation;

import org.sike.permission.config.PermissionImportBeanDefinitionRegistrer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = PermissionImportBeanDefinitionRegistrer.class)
public @interface EnableAiCloudPermission {
}
