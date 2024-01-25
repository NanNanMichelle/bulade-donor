## Model 规约

`model` 一般用于与数据库表的一一对应，对于连表查询的情况，也可以定义为一个 `model`。

### 示例

#### 单表操作

例如 `t_users` 表包含 `id`, `username`, `password` 三个个字段，那么对应的 `model` 为：

```java
class User {

    private Long id;

    private String username;

    private String password;

}
```

#### 连表操作

例如 `t_posts` 表包含 `id`, `user_id`, `title`, `content` 四个字段，`user_id` 与上面的 `t_users` 表关联，那么如果需要展示 `t_users` 和 `t_posts` 的关联信息时，可以创建对应的 `model` 为：

```java
class Post {

    private Long id;

    private Long userId;

    private String title;

    private String content;

}
```

```java
class UserPost {

    private Long id;

    private Long userId;

    private String username;

    private String title;

    private String content;

}
```
