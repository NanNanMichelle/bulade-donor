## Mapper 方法命名规范

### 插入

* `insert`

### 统计

* `countAll`
* `countByXXX`
* `countXXXByXXX`

### 更新

* `updateById`
* `updateByXXX`
* `updateXXXByXXX`

### 删除

* `deleteById`
* `deleteByXXX`

### 查询

#### 单条结果

* `selectById`
* `selectByXXX`
* `selectXXXById`

#### 多条结果

* `listByXXX`

示例：

`ProductsMapper`

```java
interface ProductsMapper {

    // 错误，本身就在 Products 领域下，无需再写 ProductId
    selectByProductId(Long id);

    // 正确，领域范围内已经有清晰定义
    selectById(Long id);

    // 错误，本身就在 Products 领域下，无需再写 Product
    selectProductById(Long id);

    // 正确，领域范围内已经有清晰定义
    selectById(Long id);

}
```

