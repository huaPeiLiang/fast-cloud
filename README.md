# V2:快速向导

基于v1增加了RabbitMQ、Bus、Elasticsearch支持。

### [代码规范](https://github.com/huaPeiLiang/fast-cloud/wiki)

###    模块职责

模块名称 | 端口 |  职责  
-|-|-
eureka | 9991 | 注册中心模块，提供服务注册、服务发现 |
config | 9992 | 配置中心模块，提供公共的配置 |
common |      | 底层公共模块，以jar包形式存在，提供公共的工具类、实体类、枚举类、请求响应类、异常类 |
api    |      | 服务间调用公共模块，以jar包形式存在，提供业务模块调用入口、降级处理 |
elasticsearch-api |      | 提供elasticsearch的服务调用 |
account| 7001 | 业务处理，提供示范代码 |
record | 7002 | 业务处理，提供示范代码 |
monitor| 7003 | 监控模块，提供各模块的健康状态 |
elasticsearch | 7004 | 搜索引擎实现模块，提供搜索引擎相关实现 |
facade |  80  | 对外模块，提供外界调用微服务的入口、拦截器、过滤器等 |

----

###    模块依赖关系

弱依赖：不会出现编译异常，在具体业务调用时，会因为弱依赖的服务不正常，而返回错误的响应。在代码规范中会详细讲述如何处理弱依赖关系。

eureka不依赖于任何模块。

config依赖于eureka。

common不依赖于任何模块。

elasticsearch-api不依赖于任何模块。

api依赖于common。

elasticsearch依赖于elasticsearch-api模块。

account、record依赖于eureka、config、api，业务模块之间弱依赖。

facade依赖于作eureka、config、api、elasticsearch-api。弱依赖于业务模块。

monitor依赖于作eureka。弱依赖于业务模块。

----

###    技术栈版本

spring-boot：2.1.0.RELEASE

spring-cloud：Greenwich.M3

lombok：1.18.12

txlcn-tc: 5.0.2.RELEASE

txlcn-txmsg-netty: 5.0.2.RELEASE

----

###    启动项目

一、创建表，建表语句在common模块中model/sql.text中。

二、修改account、record、facade模块中的数据库、Redis、TX-LCN、RabbitMQ、Elasticsearch配置。

三、启动顺序eureka -> config -> account、record、elasticsearch、facade -> monitor
    
----

###   测试
####    Hystrix测试接口(可以修改facade模块hystrix配置来调整降级、断路)

```json
测试hystrix断路器成功响应
接口地址：http://127.0.0.1/test/hystrix-success
接口类型：GET
请求参数：无
```

```json
测试hystrix断路器超时响应
接口地址：http://127.0.0.1/test/hystrix-timeout
接口类型：GET
请求参数：无
```

```json
测试hystrix断路器失败响应
接口地址：http://127.0.0.1/test/hystrix-error
接口类型：GET
请求参数：无
```

####    负载均衡测试接口（启动两个account模块，并配置不同的端口号）

在v2中，这个接口也用来测试配置自动刷新，具体步骤如下：

先调用该接口，然后修改公共配置bus.renewal的值。重启config之后调用account模块的actuator/bus-refresh接口。再次请求该接口配置刷新。（关于调用触发配置更新接口的时机，可以自行选择。）

```json
负载均衡测试
接口地址：http://127.0.0.1/test/ribbon-test
接口类型：GET
请求参数：无
请求响应：返回不同的端口号
```

####    分页测试接口

```json
分页测试接口
接口地址：http://127.0.0.1/account/page
接口类型：POST
请求参数：{}
```

####  分布式事务测试接口（需要先在account模块的AccountServiceImpl类中该方法手动抛错）

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

#### RabbitMQ测试接口

```json
向队列中添加消息
接口地址：http://127.0.0.1/test/mq
接口类型：GET
请求参数：无
```

#### Elasticsearch测试类

```json
获取品牌所有商品的平均售价
接口地址：http://127.0.0.1/elasticsearch/avg/price-by-brand
接口类型：GET
请求参数：无
```

注意在进行调用该测试接口之前，需要进行前置条件准备。找到elasticsearch模块中的test文件夹下EsDemoApplicationTest测试类。

一、执行createIndex测试方法创建索引、类型。

二、执行insertList测试方法批量插入数据。

三、调用测试接口。
