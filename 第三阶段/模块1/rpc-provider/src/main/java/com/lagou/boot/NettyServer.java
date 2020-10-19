package com.lagou.boot;

import com.lagou.handler.UserServiceHandler;
import com.lagou.rpcRequest.JSONSerializer;
import com.lagou.rpcRequest.RpcDecoder;
import com.lagou.rpcRequest.RpcRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName NettyServer
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-18 14:56
 * @Version V1.0
 **/

@Component
public class NettyServer implements InitializingBean, ApplicationContextAware {


    private ApplicationContext applicationContext;


    //创建一个方法启动服务器
    public void startServer(String ip, int port) throws InterruptedException {
        //1.创建两个线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //2.创建服务端的启动引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //3.配置启动引导对象
        serverBootstrap.group(bossGroup, workGroup)
                //设置通道为NIO
                .channel(NioServerSocketChannel.class)
                //创建监听channel
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //获取管道对象
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        //给管道对象pipeLine 设置编码
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        //把我们自顶一个ChannelHander添加到通道中
                        pipeline.addLast(new UserServiceHandler(applicationContext));
                    }
                });

        System.out.println("["+ip+":"+port+"]服务端已启动。。。");


        //4.绑定端口
        serverBootstrap.bind(ip, port).sync();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startServer("127.0.0.1", 8998);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
