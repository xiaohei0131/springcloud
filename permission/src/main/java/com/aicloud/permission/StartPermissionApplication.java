package com.aicloud.permission;

import com.aicloud.permission.annotation.EnableAicloudPermission;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAicloudPermission(packages = "com.aicloud")
public class StartPermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartPermissionApplication.class, args);
    }
}
