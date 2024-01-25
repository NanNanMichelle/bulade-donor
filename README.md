## 项目简介
- 献血服务 基于SpringBoot 3.2.1 提供一整套标准的Restful API
- 涵盖xxxxxxx等服务功能
- 提供免费基础服务，支持按需定制和扩展，满足不同客户需要

🙂 所有功能，都需要通过 单元测试 保证高质量。

## 主要特性
- [x] Maven多项目依赖，模块及插件分项目，尽量松耦合，方便模块升级、增减模块。
- [ ] 完善的XSS防范及脚本过滤，杜绝XSS攻击
- [ ] 完善的日志记录体系简单注解即可实现
- [ ] 国际化支持，服务端及客户端支持
- [ ] 一键生成功能（包括控制器、模型、视图、菜单等）
- [ ] 支持服务监控，数据监控，缓存监控功能
- [ ] 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志
- [ ] 支持多数据源，简单配置即可实现切换
- [ ] 待定

## 技术栈
#### 1、系统环境
* JDK 17
* Maven 3

#### 主框架
* 应用开发框架: Spring Boot 3.2.1
* Spring 安全框架：Security  6.2.1
* Token生成与解析：jwt
* 日志：logback 1.4.14

#### 持久层
* ORM 框架： MyBatis 3.x
* 数据库：MySQL 8.2.0
* 缓存：Redis 

### 中间件 x
* 工作流引擎：Flowable 7
* 任务调度组件：Quartz 2

### 单元测试
* Java 单元测试框架 : JUnit5
* Java Mock 框架 Mockito 5

#### 其他工具
* Swagger 文档: Springdoc 2.3.0
* 待定：Java Bean 转换: MapStruct 1.5.5.Final [UserConvert.java](bulade-donor-system%2Fsrc%2Fmain%2Fjava%2Fcom%2Fbulade%2Fdonor%2Fsystem%2Fconvert%2FUserConvert.java)
* 消除冗长的 Java 代码: Lombok 1.18.30
* Spring REST Docs 3.2.x [示例](bulade-donor-application%2Fsrc%2Fmain%2Fasciidoc%2Findex.adoc)
* 数据库版本控制管理工具：Flyway 9.22.3 支持MySql 8.1.0 
[FlyWay.md](docs%2Fdictionary%2FFlyWay.md)


### 系统监控

## 产品

### 模块
```
com.bulade.donor
├── common            // 工具类
│       └── core                          // 核心控制
│       └── enums                         // 通用枚举
│       └── exception                     // 通用异常
│       └── utils                         // 通用类处理
│       └── annotation                    // 自定义注解
│       └── constant                      // 通用常量
├── framework         // 框架拓展
│       └── security                      // 权限控制
│           └── config
│           └── core
│       └── xss
│       └── job                           // 定时任务
│       └── ip                            // IP 拓展、区域
│       └── cache                         // 缓存
│       └── upload                        // 附件上传
│       └── sms                           // 短信
│       └── mail                          // 邮件
│       └── pay                           // 支付：微信、支付宝
│       └── face                          //人脸识别
│       └── flowable
│       └── monitor
│       └── mq
├── system            // 系统功能
│       └── bo
│       └── dto
│       └── convert
│       └── model
│       └── dao
│       └── service
│           └── impl
├── infra             // 基础设施
├── member            // 会员中心
├── biz               // 业务
│       └── navigation                     // 献血导航
│       └── ranking                        // 献血排行
│       └── appointment                    // 预约登记
│       └── xxxxxx
├── application       // Web服务端
│       └── controller.admin               //管理员
│       └── controller.devs                //研发
│       └── controller.v1                  //客户
│       └── controller.xxxx                //xxx
│       └── payload
│           └── request
│           └── response
│       └── manage

```

![献血服务.png](docs%2F%E7%8C%AE%E8%A1%80%E6%9C%8D%E5%8A%A1.png)

### 功能
* [系统功能](bulade-donor-system%2FREADME.md)
* [基础设施](bulade-donor-infra%2FREADME.md)
* [会员中心](bulade-donor-member%2FREADME.md)
* 数据报表
* 业务服务
  * 献血导航
  * 预约登记
  * 结果查询
  * 献血足迹
  * 献血排行

### 代码规范
* [代码规范](https://github.com/bulade-platform/Java-specification/blob/main/README.md)
