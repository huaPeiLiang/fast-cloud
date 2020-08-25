# fast-cloud

### 关于本项目
大家好，我是Martin。最初我创建了[spring-cloud-start](https://github.com/huaPeiLiang/spring-cloud-start)，希望通过源码及过程讲解帮助想接触SpringCloud的人学习该架构。同时也希望给需要转用SpringCloud的开发者提供快速搭建项目的雏形。由于spring-cloud-start项目主要面向学习者，并未注重代码规范且大量技术冗余在了一起。考虑到实际项目中使用技术的巨大差异，于是fast-cloud应运而生。在fast-cloud中通过多个分支集成不同的技术组合、降低依赖，以达到线上项目需求。同时每个分支会提供快速向导及代码规范，现在你可以使用它了。

### 分支选择
* #### [V1](https://github.com/huaPeiLiang/fast-cloud/tree/v1)：Eureka、Config、OpenFeign、Ribbon、Hystrix、Tx-Lcn、JPA、Redis、Mysql、JWT
  * 前提条件：Tx-Lcn分布式事务基于TM服务才能发挥作用，需要提前搭建好TM服务。
  * 提供：异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、PDF水印 替换 电子签章、邮件发送、JWT加密解密、断路器测试接口、JPA查询、分页测试接口、分布式事务测试接口等。
  
* #### [V2](https://github.com/huaPeiLiang/fast-cloud/tree/v2)：Eureka、Config、OpenFeign、Ribbon、Hystrix、Tx-Lcn、JPA、Redis、Mysql、JWT、RabbitMQ、Bus、Elasticsearch
  * 前提条件：Tx-Lcn分布式事务基于TM服务才能发挥作用，需要提前搭建好TM服务。
  * 异常处理及统一返回、Radis相关操作及分布式锁、Http远程服务调用、服务配置自动刷新、PDF水印 替换 电子签章、邮件发送、JWT加密解密、断路器测试接口、JPA查询、分页测试接口、分布式事务测试接口、RabbitMQ测试接口、Elasticsearch测试接口等。
  
* #### [V3]()：Nacos、OpenFeign、Ribbon、Sentinel、MyBatis-Plus、Redis、Mysql、JWT
  * 即将到来...

### 规范文档
#### [代码规范](https://github.com/huaPeiLiang/fast-cloud/wiki)

### 交流讨论
邮箱：martin.hua@foxmail.com
