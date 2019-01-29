package org.sike.permission.config;

import org.sike.permission.server.PermissionService;
import org.sike.permission.server.PermissionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

@Configuration
@ConditionalOnBean(PermissionServiceImpl.class)
@EnableWebMvc
public class PermissionServerConfiguration {
    /**
     * hessian没有注册表，不需要设置 serviceName
     */
    @Bean(name = "hessianServiceExporter")
    public HessianServiceExporter hessianServiceExporter(PermissionServiceImpl permissionServiceImpl) {
        HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
        hessianServiceExporter.setService(permissionServiceImpl);
        hessianServiceExporter.setServiceInterface(PermissionService.class);
        return hessianServiceExporter;
    }

    /**
     * 需要配置一个URL映射来确保DispatcherServlet把请求转给HessianServiceExporter
     */
    @Bean
    public HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.setProperty("/permission.service", "hessianServiceExporter");
        handlerMapping.setMappings(mappings);
        return handlerMapping;
    }
}
