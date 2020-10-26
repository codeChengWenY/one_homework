package com.lagou.boot;

import com.lagou.handler.UserServiceHandler;
import com.lagou.pojo.RPCResponse;
import com.lagou.rpcRequest.JSONSerializer;
import com.lagou.rpcRequest.RpcDecoder;
import com.lagou.rpcRequest.RpcEncoder;
import com.lagou.rpcRequest.RpcRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName NettyServer
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-18 14:56
 * @Version V1.0
 **/

@Slf4j
public class NettyServer implements InitializingBean {


    @Setter
    private String nettyServerHostName = "127.0.0.1";

    @Setter
    private int nettyServerPort = 8000;



    @Autowired
    private UserServiceHandler serverHandler;

    @Autowired
    private ServerRegistry serverRegistry;


    //创建一个方法启动服务器
    public void startServer() throws InterruptedException {
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
                        pipeline.addLast(new RpcEncoder(RPCResponse.class, new JSONSerializer()));
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        //把我们自顶一个ChannelHander添加到通道中
                        pipeline.addLast(serverHandler);
                    }
                });

        //4.绑定端口
        serverBootstrap.bind(nettyServerHostName, nettyServerPort).sync();
        System.out.println("[" + nettyServerHostName + ":" + nettyServerPort + "]服务端已启动。。。");

        // 注册服务到 ZooKeeper
        serverRegistry.registry(nettyServerHostName, nettyServerPort);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.startServer();
    }


}
