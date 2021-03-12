                                                                     ElasticSearch学习笔记
---
categories: MQ
--- 

-  概念

    Elaticsearch简称为ES,是一个开源的可扩展的分布式的**全文检索引擎**，它可以近乎实时的存储、检索数据。本身扩展性很好，可扩展到上百台服务器，处理**PB**级别的数据。ES使用Java开发并使用Lucene作为其核心来实现索引和搜索的功能，但是它通过简单的**RestfulAPI**和**javaAPI**来隐藏Lucene的复杂性，从而让全文搜索变得简单。

- 功能

  1. 分布式的搜索引擎

     分布式：Elasticsearch自动将海量数据分散到多台服务器上去存储和检索

     搜索：百度、谷歌，站内搜索

​           2.  全文检索

​              提供模糊搜索等自动度很高的查询方式，并进行相关性排名，高亮等功能

​           3. 数据分析引擎（分组聚合）  

​         电商网站，最近一周笔记本电脑这种商品销量排名top10的商家有哪些？新闻网站，最近1个月访问量排名top3的新闻板块是哪些.

​          4 . 对海量数据进行近实时的处理 

​      海量数据的处理：因为是分布式架构，Elasticsearch可以采用大量的服务器去存储和检索数据，自然而然就可以实现海量数据的处理近实时：Elasticsearch可以实现秒级别的数据搜索和分析.

​     使用实例

   创建索引

    ```java
PUT my_index
{
  "settings": {
      "属性名"："属性值"
  }
}
    ```

判断索引是否存在

```
HEAD /my_index
```

 查看索引

 ```
GET /my_index
 ```

  批量查看索引

```
GET /索引名称1，索引名称2
```

 查看所有索引

```
GET _all
```

```
GET /_cat/indices?v
```

 

 删除索引库

```
DELETE /my_indx
```



**字段映射**

 ```
PUT /索引库名/_mapping
{
 "properties":{
    "字段名"{
    "type":"类型",
    "index":true,
    "store":true,
    "analyzer":"分词器"
   }
 }
}
 ```

eg:

```json
PUT /lagou-company-index
PUT /lagou-company-index/_mapping
{
  "properties": {
    "name": {
      "type": "text",
      "analyzer": "ik_max_word"
    },
    "job": {
      "type": "text",
      "analyzer": "ik_max_word"
    },
    "logo": {
      "type": "keyword",
      "index": false
    },
    "payment": {
      "type": "float"
    }
  }
}
```

  **文档的增删改查及局部更新**

文档，即索引库中的数据，会根据规则创建索引，将来用于搜索。可以类比做数据库中的一行数据

语法

```
POST /索引名称/_doc 
{ 
"field":"value" 
}
```



```
POST /lagou-company-index/_doc/1
{
  "name":"百度",
  "job":"小度用户运营经理",
  "payment":"30000",
"logo":"http://www.lgstatic.com/thubnail_120x120/i/image/M00/21/3E/CgpFT1kVdzeAJNbUAABJB7x9sm8374.png"
}

```

**查看单个文档**

```
GET /索引名称/_doc/{id}
```

**查看所有文档**

```
POST /索引名称/_search 
{
  "query":{ 
  "match_all": { 
   }
  } 
}
```

**更新文档**

  局部更新

```
POST /lagou-company-index/_update/1
{
  "doc":{
    "logo":"1111" 
  }
}
```



  全部更新

​      新增请求 改为PUT  就是修改了



​        DSL语句

```
POST /lagou-company-index/_search
{
  "query": {
    "match_all": {}
  }
}
```



```
POST /lagou-company-index/_search
{
  "query": {
    "match": {
      "name": "百度"
    }
  }
}
```

match 类型查询，会把查询条件进行分词，然后进行查询,多个词条之间是or的关系



**短语搜索** (match phrase query)

   match_phrase 查询用来对一个字段进行短语查询，可以指定 analyzer、slop移动因子

```
POST /lagou-company-index/_search
{
  "query": {
    "match_phrase": {
      "name":{
         "query": "百 12",
         "slop": 2  
      }
    }
  }
}
```



**query_string** **查询**

Query String Query提供了无需指定某字段而对文档全文进行匹配查询的一个高级查询,同时可以指定在哪些字段上进行匹配。

```
GET /lagou-company-index/_search
{
  "query": {
    "query_string": {
      "query": "30000"
    }
  }
}
```



**词条级搜索** **(term-level queries)**

可以使用**term-level queries**根据结构化数据中的精确值查找文档。结构化数据的值包括日期范围、IP地址、价格或产品ID。

与全文查询不同，term-level queries不分析搜索词。相反，词条与存储在字段级别中的术语完全匹配。

```
POST /lagou-company-index/_search
{
  "query": {
    "term": {
      "name": {
        "value": "12"
      }
    }
  }
}
```







