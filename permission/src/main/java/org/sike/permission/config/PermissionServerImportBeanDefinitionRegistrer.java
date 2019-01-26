package org.sike.permission.config;

import org.sike.permission.server.AiCloudPermissionServer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class PermissionServerImportBeanDefinitionRegistrer implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{AiCloudPermissionServer.class.getName(),PermissionMapping.class.getName()};
    }
}
