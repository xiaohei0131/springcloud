package org.sike.permission.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AiCloudPermission {
    String name();

    String desc();
}
