                                                                 MongoDB 和Neo4j  学习笔记

整理 一些常用 命令

查看数据库 

```
show dbs; 
```

 切换数据库 如果没有对应的数据库则创建 

``` 
use 数据库名;
```

创建集合 

``` 
 db.createCollection("集合名")
```

查看集合 

```   
show tables; 
show collections; 
```

删除集合 

``` 
db.集合名.drop(); 
```

删除当前数据库 

```
db.dropDatabase();
```

插入多条数据

```
db.集合名.insert([文档,文档])
```

查询

```
db.集合名.find({key1:value1, key2:value2}).pretty()
```

分页查询

```
db.集合名.find({条件}).sort({排序字段:排序方式})).skip(跳过的行数).limit(一页显示多少数据)
```

**Neo4j**

​       Neo4j是一个开源的 无Shcema的 基于java开发的 图形数据库，它将结构化数据存储在图中而不是表中。它是一个嵌入式的、基于磁盘的、具备完全的事务特性的Java持久化引擎。程序数据是在一个面向对象的、灵活的网络结构下，而不是严格、静态的表中,但可以享受到具备完全的事务特性、企业级的数据库的所有好处。

**Neo4j** **主要构建块**

- 节点

- 属性

- 关系

- 标签

- 数据浏览器

**Neo4j CQL**

CQL代表Cypher查询语言。 像关系型数据库具有查询语言SQL，Neo4j使用CQL作为查询语言。

- 它是Neo4j图形数据库的查询语言。

- 它是一种声明性模式匹配语言。

- 它遵循SQL语法。

- 它的语法是非常简单且人性化、可读的格式。

![image-20210106103034711](https://gitee.com/adc123321/blog_img/raw/master/image/202101/06/103036-44145.png)