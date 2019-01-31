package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PermissionPool {

    private static class PermissionPoolInstance {
        private static final PermissionPool instance = new PermissionPool();
    }

    private PermissionPool() {
    }

    public static PermissionPool getInstance() {
        return PermissionPoolInstance.instance;
    }

    private Map<String, JSONArray> permissions = new HashMap<>();
    private Map<String, Integer> permissionVersion = new HashMap<>();

    protected Map<String, JSONArray> getPermissions() {
        return permissions;
    }

    protected Map<String, Integer> getPermissionVersion() {
        return permissionVersion;
    }

    public JSONArray getPermissionByClient(String client) {
        return permissions.get(client);
    }

    public Map<String, JSONArray> getAllPermission() {
        return permissions;
    }


    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    public String getPathPermission(HttpServletRequest request, String client, String path) {
        if (StringUtils.isEmpty(path)) {
            path = request.getRequestURI();
        }
        if (StringUtils.isEmpty(client)) {
            client = "global";
        }
        String method = request.getMethod();
        String consumer = request.getHeader("Content-Type");
        String produce = request.getHeader("Accept");
        JSONObject permission;
        JSONArray plist = permissions.get(client);
        if (null == plist){
            return "";
        }
        JSONArray urls;
        JSONArray methods;
        JSONArray consumers;
        JSONArray produces;
        for (int i = 0; i < plist.size(); i++) {
            permission = plist.getJSONObject(i);
            methods = permission.getJSONArray("methods");
            if (!(methods.size() == 0 || methods.contains(method))) {
                continue;
            }
            urls = permission.getJSONArray("urls");
            boolean matchPath = false;
            for (int j = 0; j < urls.size(); j++) {
                if (antPathMatcher.match(urls.getString(j), path)) {
                    matchPath = true;
                    break;
                }
            }
            if (!matchPath) {
                continue;
            }
            consumers = permission.getJSONArray("consumers");
            if (!(consumers.size() == 0 || consumers.contains(MediaType.ALL_VALUE) || consumers.contains(consumer))) {
                continue;
            }
            produces = permission.getJSONArray("produces");
            if (!(produces.size() == 0 || produces.contains(MediaType.ALL_VALUE) || produces.contains(produce))) {
                continue;
            }
            return permission.getString("permission_code");
        }
        return "";
    }
}
