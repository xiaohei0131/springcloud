# springcloud
个人学习搭建一个微服务架构，实现基本功能：
1、 注册中心（Eureka）
2、 服务网关（Zuul）
3、 服务间调用（Fegin）
-----------
## permission模块

> @EnableAiCloudPermissionServer 服务端开启权限搜集服务,通过restful接口“/permission/get”获取服务，带参数 client可查询具体服务的权限集

### 常用业务模块使用
1. @EnableAiCloudPermission  向权限搜集服务发送业务模块的所有服务

2. @AiCloudPermission(name = "<权限code,服务中全局唯一>", desc = "<权限名称>") 在controller层为restful接口增加权限
