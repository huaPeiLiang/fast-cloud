# V5:快速向导

### [代码规范](https://github.com/huaPeiLiang/fast-cloud/wiki)

###    模块职责

模块名称 | 端口 |  职责  
-|-|-
nacos | 8848 | 注册、配置中心 |
sentinel | 8080 | 限流、熔断控制台 |
seata | 8091 | 分布式事务 |
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

spring-cloud-starter-alibaba-seata： 2.1.2.RELEASE

seata-spring-boot-starter：1.4.0

spring-cloud-starter-alibaba-sentinel：0.9.0.RELEASE

sentinel-datasource-nacos：1.5.2

----

###    启动项目

一、自行准备mysql、redis、nacos、sentinel、seata等服务。
    
二、执行common模块下sql文件夹中的sql脚本。其中‘global_table’、‘branch_table’、‘lock_table’三张表需要放到seata服务引用的库中，‘undo_log’表需要放到业务库中。
    
三、修改配置文件配置，将mysql、redis、nacos、sentinel、seata等配置修改成自己的服务地址。
    
四、导入nacos配置，将common模块config文件夹下的common.properties配置文件添加到nacos中。

五、项目中的seata配置使用了nacos配置方式，需要进行如下几步操作，如果不使用nacos配置方式只需要将application.properties中的配置进行注释。如果使用nacos配置方式可以将file.conf、register.conf文件删除。

①在nacos控制台新建命名空间，将生成的命名空间ID复制下来，替换seata.config.nacos.namespace配置的值为新生成的命名空间ID。

②修改facade中config.txt文件中的配置。

③windows中执行“sh nacos-config.sh -h www.kinotools.cn -p 8848 -g SEATA_GROUP -t 49a14e41-33db-4ad6-9823-4efd220e1eeb -u nacos -w nacos
”语句，该语句将初始化nacos中seata需要的配置，-t 后面同样替换为新生成的命名空间ID。
    
六、如果需要测试sentinel限流功能，可以直接将facade模块下sentine文件夹下的配置添加到nacos中。

七、多租户拦截功能代码在api模块下的com.fast.configuration.MybatisPlusConfig中，可以根据自身需求修改租户ID获取方式及忽略拦截请求。

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

####    限流熔断测试接口

```json
限流熔断测试
接口地址：http://127.0.0.1/test/ribbon-test
接口类型：GET
请求参数：无
说    明：修改sentinel配置，争对资源名（/test/ribbon-test）进行限流配置，修改为QPS：1。如需测试熔断可在该接口中进行模拟报错测试。
请求响应：频繁调用，会出现限流错误提示。
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

####  分布式事务测试接口

```json
分布式事务测试接口
接口地址：http://127.0.0.1/account/transfer
接口类型：POST
请求参数：{
    "sourceAccountId" : 1,
    "targetAccountId" : 2,
    "amount" : 500
}
```

####  多租户测试接口

```json
多租户测试接口
接口地址：http://127.0.0.1/record/query
接口类型：GET
请求参数：{}
header参数：TenantId : 租户Id
说明：关注控制台打印sql，会自动拼接租户条件
```


