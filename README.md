# codegen

## main class

com/scd/util/MyBatisGen.java

### generatorConfigTemplate.xml

修改数据库连接信息
```xml
<jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql://localhost:3306/test1?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;serverTimezone=UTC&amp;nullCatalogMeansCurrent=true" userId="chengdu" password="chengdu">
```

### 修改tables、models

```java
private static String[] tables = {
            "t_task_param",
            "t_test",
            "t_article"
    };

    private static String[] models = {
            "TaskParam",
            "Test",
            "Article"
    };
```

注意顺序对应关系

### run main

代码会在code 路径下生成