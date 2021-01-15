​                                                    **redis常用命令**

Redis简介

Redis是一个Key-Value的存储系统，使用ANSI C语言编写。

key的类型是字符串。

value的数据类型有：

常用的：string字符串类型、list列表类型、set集合类型、sortedset（zset）有序集合类型、hash类型。

不常见的：bitmap位图类型、geo地理位置类型。

Redis5.0新增一种：stream类型

注意：Redis中命令是忽略大小写，（set SET），key是不忽略大小写的 （NAME name）

**Redis的Key的设计**

1. 用:分割

2. 把表名转换为key前缀, 比如: user:

3. 第二段放置主键值
4. 4. 第三段放置列名

比如：用户表user, 转换为redis的key-value存储

![image-20210115120041410](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120041-780160.png)

 String 常用命令

![image-20210115120231936](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120233-350827.png)

 list  常用命令

![image-20210115120634665](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120634-648466.png)

set 命令：

![image-20210115120729499](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120730-706198.png)

![image-20210115120741576](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120741-643672.png)

SortedSet(ZSet) 有序集合： 元素本身是无序不重复的

每个元素关联一个分数(score)

可按分数排序，分数可重复

![image-20210115120834530](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120835-691890.png)

**hash类型（散列表）

![image-20210115120913754](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120914-984249.png)

应用场景：

对象的存储 ，表数据的映射

![image-20210115120933218](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/120933-189593.png)

**bitmap位图类型**

![image-20210115121009723](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/121010-687561.png)

![image-20210115121020513](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/121021-843344.png)

**geo地理位置类型**

geo是Redis用来处理位置信息的。在Redis3.2中正式使用。主要是利用了Z阶曲线、Base32编码和

geohash算法

![image-20210115133242515](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/133244-775648.png)

![image-20210115133301114](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/133301-76001.png)

**stream数据流类型**

stream是Redis5.0后新增的数据结构，用于可持久化的消息队列。

![image-20210115133337748](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/133337-948273.png)

![image-20210115133353890](https://gitee.com/adc123321/blog_img/raw/master/image/202101/15/133354-217675.png)