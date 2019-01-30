package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//@RestController
public class AiCloudPermissionServer {
    private static Map<String, JSONArray> PERMISSIONS = new HashMap<>();
    private static Map<String, Integer> PERMISSION_VERSION = new HashMap<>();

    @PostMapping("/permission/register")
    @ResponseBody
    public void permissionRegister(@RequestParam("client") String client, @RequestParam("permission") String permission, @RequestParam(value = "version", required = false) Integer version) {
        if (PERMISSION_VERSION.get(client) == null || PERMISSION_VERSION.get(client) < version) {
            PERMISSION_VERSION.put(client, version);
            PERMISSIONS.put(client, JSONArray.parseArray(permission));
        }
    }

    @GetMapping("/permission/get")
    @ResponseBody
    public Object getAllPermission(@RequestParam(value = "client", required = false, defaultValue = "") String client) {
        return StringUtils.isEmpty(client) ? PERMISSIONS : PERMISSIONS.get(client);
    }
}
