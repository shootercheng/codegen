codegen

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



# CODE GEN 增强版  

> 2020-05-29

## Main Class

com/scd/util/MyBatisProGen.java

## 使用步骤

### step1    修改配置文件   resources/templates/gen.properties



```python
# 驱动路径，可以为空，空值就会取默认mysql 驱动， 路径 jar/mysql-connector-java-5.1.47.jar
driverPath=

# db connect properties 数据库连接属性
# 注意在 xml 中 & 符号需要使用 &amp
# 关于转义字符 参考 https://www.w3school.com.cn/html/html_entities.asp)
driverClass=com.mysql.jdbc.Driver
jdbcUrl=jdbc:mysql://localhost:3306/test1?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;serverTimezone=UTC&amp;nullCatalogMeansCurrent=true
username=chengdu
password=chengdu

# model、xml、mapper gen path and pkg
# targetPath 生成代码路径， modelPkg 生成模型包路径
targetPath=code
modelPkg=scd.model
xmlPkg=scd.xml
mapperPkg=scd.mapper

# table、model、table tag
# 需要生成数据库的表，以及对应的模型名字，注意顺序对应关系
tables=t_article|t_task_param|t_test|t_user
models=ArticlePO|TaskParamPO|TestPO|UserPO
tableTagTemplate=<table tableName="${table}" domainObjectName="${model}" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>


# gen config path
# 生成 xml 配置文件路径， 模板文件固定在 resources 目录下面
configPath=temp
```

## step2 run main

MyBatisProGen  -->  run

代码文件会在 configPath 对应路径下生成