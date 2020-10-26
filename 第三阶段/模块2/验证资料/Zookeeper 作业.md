                                 Zookeeper 作业

先看效果

作业1 服务器端 启动

![image-20201026141311945](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/141314-132324.png)

客户端启动 

![image-20201026141932139](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/141933-414337.png)

作业2 ：当在浏览器输入http://localhost:8080/show?name=111(实现负载效果)

![image-20201026141158316](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/141159-182026.png)

   看服务端启动是否注册到zookeeper 中（说明服务端已经在zookeeper中注册）

![image-20201026142325954](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201026142325954.png)

接下来  演示  当删除一个节点是否正常请求

![image-20201026142352802](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/142353-556851.png)



浏览器继续发出请求 http://localhost:8080/show?name=111

![image-20201026142520082](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/142520-905889.png)

 说明 负载均衡成功 同时 加入和删除节点正常响应

 整体思路：

服务端可以利用 spring 来注册不同端口的 NettyServer  bean 来启动服务端



![image-20201026142739123](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201026142739123.png)

每个bean  实现 InitializingBean接口来做一些初始化启动服务端并注册到在Zookeeper 的工作。

![image-20201026143046782](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201026143046782.png)

接下来是客户端

 1 首先是获取到注册到Zookeeper 的服务端地址信息

![image-20201026143423307](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20201026143423307.png)

 2 然后  对节点的增加操作 移除 进行相应的服务端管理信息



![image-20201026143448355](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/143451-552476.png)



![image-20201026143514758](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/143515-993668.png)



这块的思路就是： 将服务端信息  和对应的服务端的Channel 管道信息   host 主机信息  lastRequestTime  上次请求时长用map对应下来。



![image-20201026145637616](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/145647-791590.png)

​    当客户端需要发送消息时

​     调用 NettyClient,send() 方法时   会从所有存在所有连接channel 管道信息   安照 LastRequestTime进行排序   获取第一条进行判断 来得到一个Channel。

![image-20201026151138377](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/154503-152283.png)

到此 服务端和 客户端连接开始 通讯。

--------------------------------------------------------------------------------------------------------------------------------------------------------------

作业三   

上效果：

项目启动效果  给一个默认的jdbc地址启动 防止没有节点报错

默认是  test 库  id 1 姓名 admin

```
create /jdbc {"url":"jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&serverTimezone=UTC","username":"root","password":"root","driver":"com.mysql.jdbc.Driver"}
```

![image-20201026160952093](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/160952-791781.png)

接下来切换 数据库连接  test_lagou数据库  看控制台输出打印

![image-20201026161222889](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/161224-94802.png)

```
set /jdbc {"url":"jdbc:mysql://localhost:3306/test_lagou?useUnicode=true&useSSL=false&serverTimezone=UTC","username":"root","password":"root","driver":"com.mysql.cj.jdbc.Driver"}
```

看到 控制台 打印 数据库1 关闭          拉勾数据库  dataSource-2 初始化了

在 看看删除    节点

![image-20201026161559666](https://gitee.com/adc123321/blog_img/raw/master/image/202010/26/161606-52420.png)

  显示正常 功能完成



整体思路 ：就是利用 zookeeper 在操作节点数据的watch机制进行相应的业务操作。