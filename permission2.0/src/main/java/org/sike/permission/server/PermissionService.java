package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;

import java.util.Map;

public interface PermissionService {

    void collectPermission(String clientName, JSONArray permissions);
    Map<String, JSONArray> getAllPermissions();
    JSONArray getPermissionsByClient(String clientName);
}
