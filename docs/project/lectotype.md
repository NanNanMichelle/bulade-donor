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
[Flyway.md](docs%2Fconvention%2FFlyway.md)
