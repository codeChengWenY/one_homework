

​                                                  **RockerMQ学习笔记**



# RocketMQ介绍

- rocketmq是阿里巴巴团队使用java语言开发的一款分布式消息中间件，是一款低延迟，高可用，拥有海量消息堆积能力和灵活拓展性的消息队列。

## RocketMQ组成部分

- rocketmq由四大核心模块组成：**producer、consumer、brokerServer、nameServer**。其中brokerServer和nameServer是rocketmq的服务端，两者一起独立的对外提供服务；而producer和consumer可看做是rocketmq的客户端，一般依附于业务应用程序

### Producer

**producer负责发送消息**。使用producer将消息发送到brokerServer，由brokerServer统一进行消息的分发。

　　rocketmq支持多种消息发送方式，如**同步消息发送**、**异步回调消息发送**、**顺序消息发送**以及**单向消息发送(异步无回调)**。除了单向消息发送，其余的发送方式均需要brokerServer返回发送结果的确认消息。

### Consumer

​     **consumer 负责消费producer发送的消息**。consumer会从brokerServer获取消息，并传递给应用程序。

　　**rocketMQ使用的消息原语是At Least Once(至少一次成功消费)，**如果一定时间内没有接收到consumer消息确认消费的响应结果，会将同一条消息再次投递给consumer。rocketmq采用ack机制保证消息的消费成功**，**所以consumer可能会多次收到同一条消息，需要consumer的业务方做好幂等防护。

​          从使用者的角度来看，consumer分为两种方式来获取信息。一种是**推模式(push consume)**，推模式看起来像是brokerServer将消息推给了consumer；另一种是**拉模式(pull consume)**，拉模式看起来像是consumer主动的去brokerServer拉取消息(实际上，推模式是基于拉模式实现的)。

### BrokerServer

**brokerServer负责消息的接收，存储和分发，**是rocketmq最核心，最重量级的组成部分。

　　为实现高可用和高吞吐，brokerServer通常采用集群部署，共同对外提供服务。

### NameServer

​           **nameServer负责提供路由元数据**。例如，brokerServer通常是集群部署的，其拓扑结构会经常的发生变化。如果每次集群中broker机器的上下线都需要通知所有的消费者、生产者，效率太低。

　　因此，rocketmq引入了nameServer作为brokerServer路由信息的维护者，broker的每次上下线都和nameServer通信，由nameServer来维护broker的路由信息，而producer和consumer通过访问nameServer获得对应broker的访问地址后，再向对应的broker发起请求。**nameServer解除了broker和客户端的耦合依赖关系，大大提高了效率**

 在其它主流消息队列中也存在着类似的维护元信息功能的组件，如zookeeper等。rocketmq的设计者认为zk的功能过于强大，杀鸡焉用牛刀，通过一个精简版的元数据服务nameServer，以减少对外部系统的耦合依赖，得以提供更可靠的服务。

　　nameServer同样能以集群形式对外提供服务。但和zk集群不同的是，集群内的nameServer服务器并不会互相通信，而是保持相互独立。

![image-20210301135510557](https://gitee.com/adc123321/blog_img/raw/master/image/202103/01/140503-745030.png)

后台 启动 nameserver

```
nohup mqnamesrv &
```

  后台 启动 broker

   ``` 
nohup mqbroker -n 117.50.7.7:9876 -c /opt/rocketmq/conf/broker.conf  &
   ```





