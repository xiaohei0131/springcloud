package org.sike.permission.config;

import org.sike.permission.server.AiCloudPermissionServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@Configuration
//@ConditionalOnBean(PermissionServerMarker.class)
public class PermissionMapping implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void permission() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            String handler = AiCloudPermissionServer.class.getName();
            Object controller = applicationContext.getBean(handler);
            if (controller == null) {
                return;
            }
            //注册Controller
            Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
                    getDeclaredMethod("detectHandlerMethods", Object.class);
            method.setAccessible(true);
            method.invoke(requestMappingHandlerMapping, handler);
        }
    }
}
