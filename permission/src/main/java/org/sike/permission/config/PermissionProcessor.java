package org.sike.permission.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.sike.permission.annotation.AiCloudPermission;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;

@Lazy
public class PermissionProcessor implements ApplicationContextAware {
    //    private final Log logger = LogFactory.getLog(PermissionProcessor.class);
    private ApplicationContext applicationContext;

    String serverUrl;

    String clientName;
    String version;

    private static JSONArray permissionList = new JSONArray();
    private static Set<String> permissionSet = new HashSet<>();

    @PostConstruct
    public void postProcessBeanFactory() throws BeansException {
        if (StringUtils.isEmpty(serverUrl)) {
            throw new FatalBeanException("Not found \"aicloud.permission.server.url\".Please check your configuration.");
        }
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 取得对应Annotation映射，BeanName -- 实例
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        RequestMappingInfo info;
        HandlerMethod method;
        RequestMethodsRequestCondition methodsRequestCondition;
        PatternsRequestCondition patternsRequestCondition;
        AiCloudPermission permission;
        JSONObject permissionObj;
        List<String> urls;
        List<String> methods;
        List<String> consumers;
        List<String> produces;
        List<String> headers;
        List<String> params;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            info = m.getKey();
            method = m.getValue();
            patternsRequestCondition = info.getPatternsCondition();
            permission = method.getMethod().getAnnotation(AiCloudPermission.class);
            if (permission != null) {
                if (permissionSet.contains(permission.name())) {
                    throw new BeanInstantiationException(method.getClass(), "Duplicate permission [" + permission.name() + "]");
                }
                permissionSet.add(permission.name());
                permissionObj = new JSONObject();
                permissionObj.put("permission_code", permission.name());
                permissionObj.put("permission_name", permission.desc());
                urls = new ArrayList<>();
                for (String url : patternsRequestCondition.getPatterns()) {
                    urls.add(url);
                }
                permissionObj.put("urls", urls);
                methodsRequestCondition = info.getMethodsCondition();
                methods = new ArrayList<>();
                for (RequestMethod requestMethod : methodsRequestCondition.getMethods()) {
                    methods.add(requestMethod.toString());
                }
                permissionObj.put("methods", methods);

                consumers = new ArrayList<>();
                for (MediaType mediaType : info.getConsumesCondition().getConsumableMediaTypes()) {
                    consumers.add(mediaType.toString());
                }
                permissionObj.put("consumers", consumers);

                produces = new ArrayList<>();
                for (MediaType mediaType : info.getProducesCondition().getProducibleMediaTypes()) {
                    produces.add(mediaType.toString());
                }
                permissionObj.put("produces", produces);


                headers = new ArrayList<>();
                for (NameValueExpression<String> nve : info.getHeadersCondition().getExpressions()) {
                    headers.add(nve.toString());
                }
                permissionObj.put("headers", headers);


                params = new ArrayList<>();
                for (NameValueExpression<String> nve : info.getParamsCondition().getExpressions()) {
                    params.add(nve.toString());
                }
                permissionObj.put("params", params);

                permissionList.add(permissionObj);
            }
        }

        MultiValueMap<String, Object> mvpParams = new LinkedMultiValueMap<>();
        mvpParams.add("permission", JSONArray.toJSONString(permissionList));
        mvpParams.add("client", clientName);
        mvpParams.add("version", version);
        for (String url : serverUrl.split(",")) {//多个服务器轮流上报
            PermissionRunnable permissionRunnable = new PermissionRunnable(url, mvpParams);
            new Thread(permissionRunnable).start();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
