# fast-cloud

### 关于本项目
大家好，我是Martin。最初我创建了[spring-cloud-start](https://github.com/huaPeiLiang/spring-cloud-start)，目的是学习spring clou各种组件的同时将他们整合并应用起来。但是由于集成组件越来越多，以及互相之间功能重复使得原先的项目变成臃肿不堪。fast-cloud便应运而生，通过分支管理让组件集成到不同的项目中。大家可以根据自己需求选择相应的版本，现在你可以使用它了。

### 分支选择
#### 切换分支查看《快速开始》向导及代码
* #### [V1](https://github.com/huaPeiLiang/fast-cloud/tree/v1)：Eureka、Config、OpenFeign、Ribbon、Hystrix、Tx-Lcn、JPA、Redis、Mysql、JWT
  * **前提条件：** 有可使用的Mysql服务、redis服务、TM服务（Tx-Lcn分布式事务基于TM服务才能发挥作用）。
  * **提供：** 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、PDF水印 替换 电子签章、邮件发送、JWT加密解密、断路器测试接口、JPA查询、分页测试接口、分布式事务测试接口等。
  
* #### [V2](https://github.com/huaPeiLiang/fast-cloud/tree/v2)：Eureka、Config、OpenFeign、Ribbon、Hystrix、Tx-Lcn、JPA、Redis、Mysql、JWT、RabbitMQ、Bus、Elasticsearch
  * **前提条件：** 有可使用的Mysql服务、redis服务、TM服务（Tx-Lcn分布式事务基于TM服务才能发挥作用）、Elasticsearch服务、RabbitMQ服务。
  * **提供：** 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、服务配置自动刷新、PDF水印 替换 电子签章、邮件发送、JWT加密解密、断路器测试接口、JPA查询、分页测试接口、分布式事务测试接口、RabbitMQ测试接口、Elasticsearch测试接口等。
  
* #### [V3](https://github.com/huaPeiLiang/fast-cloud/tree/v3)：Nacos、OpenFeign、Ribbon、MyBatis-Plus、Redis、Mysql、JWT
  * **前提条件：** 有可使用的Mysql服务、redis服务、Nacos服务。
  * **提供：** 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、服务配置自动刷新、PDF水印 替换 电子签章、邮件发送、JWT加密解密、MybatisPlus查询、分页测试接口。

* #### [V4](https://github.com/huaPeiLiang/fast-cloud/tree/v4)：Nacos、OpenFeign、Ribbon、MyBatis-Plus、Redis、Mysql、JWT、Sentinel、Steata
  * **前提条件：** 有可使用的Mysql服务、redis服务、Nacos服务、Sentinel服务、Seata服务。
  * **提供：** 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、服务配置自动刷新、PDF水印 替换 电子签章、邮件发送、JWT加密解密、MybatisPlus查询、分页测试接口、限流熔断测试接口、分布式事务测试接口。

* #### [V5](https://github.com/huaPeiLiang/fast-cloud/tree/v5)：Nacos、OpenFeign、Ribbon、MyBatis-Plus、Redis、Mysql、JWT、Sentinel、Steata、dynamicDataSource
  * **前提条件：** 有可使用的Mysql服务、redis服务、Nacos服务、Sentinel服务、Seata服务。
  * **提供：** 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、服务配置自动刷新、PDF水印 替换 电子签章、邮件发送、JWT加密解密、动态数据源配置、多租户、MybatisPlus查询、分页测试接口、限流熔断测试接口、分布式事务测试接口。

### 规范文档
#### [代码规范](https://github.com/huaPeiLiang/fast-cloud/wiki)

### 交流讨论
邮箱：martin.hua@foxmail.com
