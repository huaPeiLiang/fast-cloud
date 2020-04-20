# 快速向导

###    各模块的职责
    eureka注册中心模块，提供服务注册、服务发现。
    config配置中心模块，提供公共的配置。
    common底层公共模块，以jar包形式存在，提供公共的工具类、实体类、枚举类、请求响应类、异常类。
    api服务间调用公共模块，以jar包形式存在，提供业务模块调用入口、降级处理。
    account、record业务模块，提供具体的业务处理接口。
    facade对外模块，提供外界调用微服务的入口、拦截器、过滤器等。
    monitor监控模块，提供各模块的健康状态。

----

###    模块间的依赖关系
    弱依赖：不会出现编译异常，在具体业务调用时，会因为弱依赖的服务不正常，而返回错误的响应。在代码规范中会详细讲述如何处理弱依赖关系。
    eureka不依赖于任何模块。
    config依赖于eureka。
    common不依赖于任何模块。
    api依赖于common。
    account、record依赖于eureka、config、api，业务模块之间弱依赖。
    facade依赖于作eureka、config、api。弱依赖于业务模块。
    monitor依赖于作eureka。弱依赖于业务模块。

----

###    框架版本
    spring-boot：2.1.0.RELEASE
    spring-cloud：Greenwich.M3
    lombok：1.18.12
    txlcn-tc: 5.0.2.RELEASE
    txlcn-txmsg-netty: 5.0.2.RELEASE

----

###    启动项目
    一、修改account、record、facade模块中的数据库、Redis、TX-LCN配置。
    二、启动顺序eureka -> config -> account、record、facade -> monitor
    
----

###   测试
####    Hystrix测试接口(可以修改facade模块hystrix配置来调整降级、断路)
    http://127.0.0.1/test/hystrix-success
    http://127.0.0.1/test/hystrix-timeout
    http://127.0.0.1/test/hystrix-error

####    负载均衡测试接口（启动两个account模块，并配置不同的端口号）
    http://127.0.0.1/test/ribbon-test
    重复调用会返回不同的端口号。

####    分页测试接口
    http://127.0.0.1/account/page

####  分布式事务测试接口（需要先在account模块的AccountServiceImpl类中该方法手动抛错）
    http://127.0.0.1/account/transfer
