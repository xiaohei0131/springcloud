package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class AiCloudPermissionServer {

    @PostMapping("/aicloud.permission.register")
    @ResponseBody
    public void permissionRegister(@RequestParam("client") String client, @RequestParam("permission") String permission, @RequestParam(value = "version", required = false) Integer version) {
        if (PermissionPool.getInstance().getPermissionVersion().get(client) == null || PermissionPool.getInstance().getPermissionVersion().get(client) < version) {
            PermissionPool.getInstance().getPermissionVersion().put(client, version);
            PermissionPool.getInstance().getPermissions().put(client, JSONArray.parseArray(permission));
        }
    }

    /*@GetMapping("/permission/get")
    @ResponseBody
    public Object getAllPermission(@RequestParam(value = "client", required = false, defaultValue = "") String client) {
        return StringUtils.isEmpty(client) ? PermissionPool.getInstance().getAllPermission() : PermissionPool.getInstance().getPermissionByClient(client);
    }*/
}
