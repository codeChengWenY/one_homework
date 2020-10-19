​     基于netty 实现自定义RPC



1.ConsumerBoot类： 启动客户端，并调用ConsumerBoot.createProxy()方法 

2.RPCConsumer类： 初始化客户端配置，并加入UserClientHandler客户端处理器，并生成代理对象执行sayHello()方法，即发送请求， 实际是交给UserClientHandler处理，主要有以下几步： （1）执行channelActive()方法，创建事件处理器上下文对象； （2）执行setParam()方法，设置请求对象，作为传递参数； （3）执行call()方法，将客户端数据写到服务端； ...服务端处理... （4）执行channelRead()，获得服务端返回数据；

 3.RpcProviderApplication类： 启动服务端，并把需要的组件加载到spring容器中，初始化调用NettyServer.startServer()方法

 4.NettyServer类： 初始化服务端配置，并加入UserServiceHandler服务端处理器，当监听到客户端有数据写入时， UserServiceHandler服务端处理器进行处理，主要有以下几步： （1）执行channelRead()方法，这里使用CGLIB动态代理生成服务端代理对象，并执行请求对象中要执行的方法， 实际执行的是由UserServiceImpl实现类处理； （2）UserServiceImpl实现类执行sayHello()方法，返回"success"；

效果如下

![image-20201019133638037](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/133640-713348.png)



关键过程：（服务端）

![image-20201019134021209](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134022-937307.png)

![image-20201019134137663](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134151-217310.png)

![image-20201019134154823](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134156-947882.png)

（客户端）

![image-20201019134238399](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134238-149549.png)

![image-20201019134259294](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134300-419178.png)

![image-20201019134323332](https://gitee.com/adc123321/blog_img/raw/master/image/202010/19/134323-158233.png)