package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class PermissionServiceImpl implements PermissionService {
    private static Map<String, JSONArray> permissions = new HashMap<>();

    @Override
    public void collectPermission(String clientName, JSONArray permission) {
        if (!permissions.containsKey(clientName)) {
            permissions.put(clientName, permission);
//            permissions.put(clientName, JSONArray.parseArray(permission));
        }
    }

    @Override
    public Map<String, JSONArray> getAllPermissions() {
        return permissions;
    }

    @Override
    public JSONArray getPermissionsByClient(String clientName) {
        return permissions.get(clientName);
    }
}
