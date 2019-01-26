package org.sike.permission.annotation;

import org.sike.permission.config.PermissionServerImportBeanDefinitionRegistrer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = PermissionServerImportBeanDefinitionRegistrer.class)
public @interface EnableAiCloudPermissionServer {
}
