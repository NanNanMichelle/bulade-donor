## 领域模型操作的基本结构

对于任何一个可以通过接口暴露给外界执行操作的领域模型，都需要有以下几个部分：

* Controller
  * Req(Request Data)
  * Resp(Response Data)
* Service
  * BO(Business Object) 业务对象。由 Service 封装业务逻辑的对象，一般为入参
  * DTO(Data Transfer Object) 数据传输对象，Service 向外传输的对象，一般为出参
* Model
* Dao(Mapper)

数据传输：
Req/Resp --> BO--> DTO --->Model

#### 代码隔离

`Service`，`Model`, `Dao` 一般不接触 `Req` 和 `Resp`，需要通过 `Req` 转成 `Model` 或者 `BO`，以及根据 `Model` 或者 `DTO` 转成 `Resp`：

```java
class UserReq {

    User toUser() {
      // return User
    }

}
```

```java
class UserResp {

    public static UserResp of(User user) {
        // return UserResp
    }

}
```

特殊情况下，需要和至少一个同事讨论是否真的需要破坏此规范
