### sql 文件命名
仅需要被执行一次的SQL命名以大写的"V"开头
- V+版本号(版本号的数字间以”.“或”_“分隔开)+双下划线(用来分隔版本号和描述)+文件描述+后缀名。
- 例如：V1.0.1__create_user.sql V1.0.2__update_user.sql

非用不可时：可重复运行的SQL，则以大写的“R”开头，后面再以两个下划线分割，其后跟文件名称，最后以.sql结尾
- R__truncate_user.sql 

### sql文件地址
database/migration

如有其它地址，在[application-dev.yml](..%2F..%2Fbulade-donor-application%2Fsrc%2Fmain%2Fresources%2Fapplication-dev.yml)
里进行添加


