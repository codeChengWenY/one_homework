​         **基于RocketMQ设计秒杀。**

​                   

整体思路：

1 使用redis 进行获取锁 

 2  获取锁后 调用RocketMQ 发送订单消息

​        -   使用redis 进行订单数量的累加 同时 redis 记录订单id

3    rocketmq 收到下单消息后 进行下单操作  等待用户支付 。

4    向RocketMQ 发送一条延时订单 检查的消息

5  延时消息到期后 

​        检查订单状态   如果支付 不进行有效订单的数量的操作

​          未完成支付  有效订单数量减少

 步骤 

-  首先 先新建springboot 项目 整合RockertMQ +Redis  

![image-20210303135500684](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/135501-574526.png)

​      

![image-20210303135630504](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/135632-29641.png)



​     redis 本地  正常 下单请求会有 orderSize 变成100 同时 会有100条订单记录

​        使用线程池默认并发500下单 操作  

![image-20210303135728017](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/135728-970039.png)



​          本地访问   http://localhost:8080/order

![image-20210303135913602](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/135914-971745.png)

![image-20210303140229125](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/140229-584742.png)

​          等待超时队列到期   不支付   订单达到100  清除 未支付的订单 数量

![image-20210303140708775](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/140914-117758.png)

​         可以看到 数量减少为0 



![image-20210303140527850](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/140528-258817.png)



此时    用户可以继续下单操作



![image-20210303140818437](https://gitee.com/adc123321/blog_img/raw/master/image/202103/03/140829-493201.png)

 这样就保证了库存不超卖的情况。







