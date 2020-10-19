package com.lagou.client;

import com.lagou.handler.UserClientHandler;
import com.lagou.rpcRequest.JSONSerializer;
import com.lagou.rpcRequest.RpcEncoder;
import com.lagou.rpcRequest.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费者
 */
public class RPCConsumer {

    //1.创建一个线程池对象  -- 它要处理我们自定义事件
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //2.声明一个自定义事件处理器  UserClientHandler
    private static UserClientHandler userClientHandler;


    //3.编写方法,初始化客户端  ( 创建连接池  bootStrap  设置bootstrap  连接服务器)
    public static void initClient() throws InterruptedException {
        //1) 初始化UserClientHandler
        userClientHandler  = new UserClientHandler();
        //2)创建连接池对象
        EventLoopGroup group = new NioEventLoopGroup();
        //3)创建客户端的引导对象
        Bootstrap bootstrap =  new Bootstrap();
        //4)配置启动引导对象
        bootstrap.group(group)
                //设置通道为NIO
                .channel(NioSocketChannel.class)
                //设置请求协议为TCP
                .option(ChannelOption.TCP_NODELAY,true)
                //监听channel 并初始化
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取ChannelPipeline
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //设置编码
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        //接收服务端返回数据“success"，添加解码器
                        pipeline.addLast(new StringDecoder());
                        //添加自定义事件处理器
                        pipeline.addLast(userClientHandler);
                    }
                });

        //5)连接服务端
        bootstrap.connect("127.0.0.1",8998).sync();
    }

    //4.编写一个方法,使用JDK的动态代理创建对象
    // serviceClass 接口类型,根据哪个接口生成子类代理对象
    public static Object createProxy(Class<?> serviceClass){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        //1)初始化客户端cliet
                        if(userClientHandler == null){
                            initClient();
                        }

                        //2)给UserClientHandler 设置param参数
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setParameters(objects);
                        System.out.println("请求内容："+rpcRequest);
                        //将请求对象客户端处理器
                        userClientHandler.setParam(rpcRequest);
                        //3).使用线程池,开启一个线程处理处理call() 写操作,并返回结果
                        Object result = executorService.submit(userClientHandler).get();

                        //4)return 结果
                        return result;
                    }
                });
    }

}
