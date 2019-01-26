package org.sike.permission.server;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
public class AiCloudPermissionServer {
    private static Map<String, JSONArray> permissions = new HashMap<>();

    @PostMapping("/permission/register")
    @ResponseBody
    /*public void permissionRegister(@RequestBody JSONObject params) {
        if (!permissions.containsKey(params.getString("client"))) {
            permissions.put(params.getString("client"), params.getObject("permission", List.class));
        }
    }*/
    public void permissionRegister(@RequestParam("client") String client, @RequestParam("permission") String permission) {
        if (!permissions.containsKey(client)) {
            permissions.put(client, JSONArray.parseArray(permission));
        }
    }

    @GetMapping("/permission/get")
    @ResponseBody
    public Object getAllPermission(@RequestParam(value = "client",required = false,defaultValue = "") String client) {
        return StringUtils.isEmpty(client) ? permissions : permissions.get(client);
    }
}
