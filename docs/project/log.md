## 系统日志
项目提供 2 类 4 种系统日志：

* 审计日志：用户的操作日志、登录日志
* API 日志：RESTful API 的访问日志、错误日志


#### 操作日志
操作日志，记录「谁」在「什么时间」对「什么对象」做了「什么事情」。
```
bulade-donor-framework
├── framework
│       └── biz
│           └── operatelog
```
* [OperateLogAspect.java](..%2F..%2Fbulade-donor-framework%2Fsrc%2Fmain%2Fjava%2Fcom%2Fbulade%2Fdonor%2Fframework%2Fbiz%2Foperatelog%2Fcore%2Faop%2FOperateLogAspect.java)
  拦声明了 @OperateLog注解的方法，异步记录日志
* 支持 @Operation注解 
* @operateLog 没有 @OperateLog 注解的情况下，只记录 POST、PUT、DELETE 的情况
* 记录 system_operate_log

#### 登录日志
登录日志，记录用户的登录、登出行为，包括成功的、失败的。
* [logger](..%2F..%2Fbulade-donor-system%2Fsrc%2Fmain%2Fjava%2Fcom%2Fbulade%2Fdonor%2Fsystem%2Flogger)
* 记录 system_login_log
* 登录类型通过 LoginLogTypeEnum 枚举，登录结果通过 LoginResultEnum 枚举

#### API 访问日志
API 访问日志，记录 API 的每次调用，包括 HTTP 请求、用户、开始时间、时长等等信息。
* filter [ApiAccessLogFilter.java](..%2F..%2Fbulade-donor-framework%2Fsrc%2Fmain%2Fjava%2Fcom%2Fbulade%2Fdonor%2Fframework%2Fweb%2Fapilog%2Ffilter%2FApiAccessLogFilter.java)
* 


#### API 错误日志
API 错误日志，记录每次 API 的异常调用，包括 HTTP 请求、用户、异常的堆栈等等信息。