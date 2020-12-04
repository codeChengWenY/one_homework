                       ##  SpringCloudAlibaba 编程题 ## 

1 、提供资料：代码工程、验证及讲解视频。

2、讲解内容包含：题目分析、实现思路、代码讲解。

3、效果展示：

1)Eureka注册中心 替换为 Nacos注册中心

2)Config+Bus配置中心 替换为 Nacos配置中心

3)Feign调用 替换为 Dubbo RPC调用

4)使用Sentinel对GateWay网关的入口资源进行限流（限流参数自定义并完成测试即可） 

作业效果同springcloud1

​     接下来 展示效果



1.  先看Nacos 注册的服务列表

   ![image-20201204100918828](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/100919-986811.png)

说明服务已经注册成功。

2. 在看Nacos 中 GateWay 的配置信息

![image-20201204101223335](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/101224-25191.png)

GateWay的成功启动说明已经从Nacos中拿到配置信息了。

3.  接下来显示页面操作

![image-20201204101613688](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/101614-915981.png)

![image-20201204101627304](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/101627-554344.png)

4. 在Sentinel Dashboard  中配置流控规则 如下图   说明 （配置Gateway 和具体服务的菜单会有不同 ，网关会显示下面箭头的部分）

![image-20201204101825430](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/101825-363667.png)

​        

![image-20201204102044478](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/102045-126699.png)

可以配置两种类型的api  一种是整个服务  一种对接口

我们对已api/user 为前缀的接口进行流控

![image-20201204102404149](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/102404-718014.png)

 这个页面会调用 api/user/info 这个接口    在没有添加流控规则多次刷新页面正常拿到数据。

5. 添加流控规则

![image-20201204102627667](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/102628-21927.png)

![image-20201204102708740](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/103152-867818.png)

 说明  每秒超过1个QPS 会进行60s的快速失败。

![image-20201204102838188](https://gitee.com/adc123321/blog_img/raw/master/image/202012/04/102838-150842.png)

   页面显示限流信息 成功。

**整体思路** ： 根据需要依赖进行整合SpringCloudAlibaba 。

