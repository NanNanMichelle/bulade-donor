## 多租户
### 使用 DATASOURCE 模式
* 基于 [dynamic-datasource](https://github.com/baomidou/dynamic-datasource-spring-boot-starter) 进行拓展实现
* 随着版本的迭代，可能需要拷贝更多的表
* 建议把拷贝到其它租户数据库的表，从主库中进行删除。目的是，主库只保留所有租户共享的全局表

#### 1、新增一个名字为 tenant-xxx 数据源
* 将主库里需要进行分的数据表，拷贝到 tenant-xxx 表下

#### 2、创建租户，选择数据源


#### 3. 创建表
在使用 DATASOURCE 模式时，数据库可以分为两种：主库、租户库。
##### 3.1 主库
* 存放所有租户共享的表。例如说：定时任务表等等
* 对应 master 数据源，配置在 application-{env}.yaml 配置文件
* 每个主库对应的 Mapper，必须添加 @Master 注解。例如说：
```
@Mapper
@Master
public interface AdminsMapper {

}
```
##### 3.2 租户库

* ① 存放每个租户的表。例如说：用户表、角色表等等。
* ② 配置数据源。
* ③ 每个主库对应的 Mapper，必须添加 @TenantDS 注解
```
@Mapper
@TenantDS
public interface AdminsMapper {

}
```
##### 3.3 租户字段
######  ① 考虑到拓展性，在使用 DATASOURCE 模式时，默认会叠加 COLUMN 模式，即还有 tenant_id 租户字段：

* 在 INSERT 操作时，会自动记录租户编号到 tenant_id 字段。
* 在 SELECT 操作时，会自动添加 WHERE tenant_id = ? 查询条件。

###### 拓展性，指的是部分【大】租户独立数据库，部分【小】租户共享数据。
###### ② 也因为叠加了 COLUMN 模式，主库的表需要根据情况添加 tenant_id 字段。

#### 4. 多数据源事务
使用 DATASOURCE 模式后，可能一个操作涉及到多个数据源。例如说：创建租户时，即需要操作主库，也需要操作租户库。
```
考虑到多数据的数据一致性，我们会采用事务的方式，而使用 Spring 事务时，会存在多数据库无法切换的问题
```
多数据源的事务方案， 比较主流的，有如下两种，都是相对重量级的方案：
* 1、使用 Atomikos 实现 JTA 分布式事务，配置复杂，性能较差
* 2、使用 Seata实现分布式事务，使用简单，性能不错，但是需要额外引入 Seata Server 服务。

###### 4.1 本地事务
考虑到项目是单体架构，不适合采用重量级的事务，因此采用 [dynamic-datasource](https://github.com/baomidou/dynamic-datasource-spring-boot-starter) 提供的 “本地事务” 轻量级方案。

它的实现原理是：自定义 @DSTransactional 事务注解，替代 Spring @Transactional 事务注解。
* 在逻辑执行成功时，循环提交每个数据源的事务。
* 在逻辑执行失败时，循环回滚每个数据源的事务。

但是它存在一个风险点，如果数据库发生异常（例如说宕机），那么本地事务就可能会存在数据不一致的问题。例如说：

① 主库的事务提交

② 租户库发生异常，租户的事务提交失败

结果：主库的数据已经提交，而租户库的数据没有提交，就会导致数据不一致。 因此，如果你的系统对数据一致性要求很高，那么请使用 Seata 方案。

