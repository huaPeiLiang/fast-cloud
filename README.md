# V4:快速向导

### [代码规范](https://github.com/huaPeiLiang/fast-cloud/wiki)

###    模块职责

模块名称 | 端口 |  职责  
-|-|-
nacos | 8848 | 注册、配置中心模块 |
common |      | 底层公共模块，以jar包形式存在，提供公共的工具类、实体类、枚举类、请求响应类、异常类 |
api    |      | 服务间调用公共模块，以jar包形式存在，提供业务模块调用入口、降级处理 |
account| 7001 | 业务处理，提供示范代码 |
record | 7002 | 业务处理，提供示范代码 |
facade |  80  | 对外模块，提供外界调用微服务的入口、拦截器、过滤器等 |

----

###    模块依赖关系

弱依赖：不会出现编译异常，在具体业务调用时，会因为弱依赖的服务不正常，而返回错误的响应。在代码规范中会详细讲述如何处理弱依赖关系。

common不依赖于任何模块。

api依赖于common。

account、record依赖于api，业务模块之间弱依赖。

facade依赖于api，弱依赖于业务模块。

----

###    技术栈版本

spring-boot：2.1.0.RELEASE

spring-cloud：Greenwich.M3

lombok：1.18.12

nacos-config：2.1.0.RELEASE

nacos-discovery：2.1.0.RELEASE

----

###    启动项目

一、创建表，建表语句在common模块中model/sql.text中。

二、nacos配置文件在common模块中的config文件夹中，需要在nacos中创建对于的配置。

三、修改account、record、facade模块中的数据库、Redis、TX-LCN、RabbitMQ、Elasticsearch配置。
    
----

###   测试
####    负载均衡测试接口（启动两个account模块，并配置不同的端口号）

```json
负载均衡测试
接口地址：http://127.0.0.1/test/ribbon-test
接口类型：GET
请求参数：无
请求响应：返回不同的端口号
```

####    动态配置测试接口

```json
动态配置测试
接口地址：http://127.0.0.1/test/get-dynamic-configuration-name
接口类型：GET
请求参数：无
说    明：更改nacos中common.properties配置文件的dynamic.configuration.name值后再进行接口调用。
请求响应：返回更改后的配置
```

####    查询测试接口

```json
查询测试接口
接口地址：http://127.0.0.1/account/get/by-id?id=
接口类型：GET
```

####    分页测试接口

```json
分页测试接口
接口地址：http://127.0.0.1/account/page
接口类型：POST
请求参数：{}
```

####  多模块调用测试接口

```json
多模块调用测试接口
接口地址：http://127.0.0.1/account/transfer
接口类型：POST
请求参数：{
    "sourceAccountId" : 1,
    "targetAccountId" : 2,
    "amount" : 500
}
```


