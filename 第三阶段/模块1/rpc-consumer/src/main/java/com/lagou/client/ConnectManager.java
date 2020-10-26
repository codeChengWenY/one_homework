package com.lagou.client;

import com.lagou.handler.UserClientHandler;
import com.lagou.pojo.RPCResponse;
import com.lagou.rpcRequest.JSONSerializer;
import com.lagou.rpcRequest.RpcDecoder;
import com.lagou.rpcRequest.RpcEncoder;
import com.lagou.rpcRequest.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ConnectManager <br/>
 * Description: <br/>
 * date: 2020-05-19 17:00<br/>
 *
 * @author colde<br />
 */
@Slf4j
@Component
public class ConnectManager {


    @Autowired
    private UserClientHandler clientHandler;

    @Getter
    private Map<String, LoadBalanceStrategy> channels = new HashMap<>();


    private List<LoadBalanceStrategy> loadBalanceStrategies = new ArrayList<>();
    private static final int MAX_RETRY = 5;

    @Getter
    private Map<String, Long> loadBalanceMap = new HashMap<>();


    @Autowired
    private CuratorFramework zkClient;

    @Builder
    @Data
    public static class LoadBalanceStrategy {

        private Channel channel;

        private String host;

        private long lastRequestTime;

    }

    public void addServer(String data) {

        ServerInfo serverInfo = getServerInfo(data);
        if (serverInfo == null) {
            return;
        }
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                //指定传输使用的Channel
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加编码器
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        //添加解码器
                        pipeline.addLast(new RpcDecoder(RPCResponse.class, new JSONSerializer()));
                        //请求处理类
                        pipeline.addLast(clientHandler);
                    }
                });
        Channel channel = connect(bootstrap, serverInfo.host, serverInfo.port, MAX_RETRY);


        LoadBalanceStrategy strategy = LoadBalanceStrategy.builder()
                .channel(channel)
                .host(data)
                .lastRequestTime(0)
                .build();

        loadBalanceStrategies.add(strategy);
        channels.put(data, strategy);

    }

    private Channel connect(Bootstrap bootstrap, String host, int port, int retry) {
        ChannelFuture channelFuture = bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接服务端成功");
            } else if (retry == 0) {
                log.error("重试次数已用完，放弃连接");
            } else {
                //第几次重连：
                int order = (MAX_RETRY - retry) + 1;
                //本次重连的间隔
                int delay = 1 << order;
                log.error("{} : 连接失败，第 {} 重连....", new Date(), order);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
        Channel channel = channelFuture.channel();
        return channel;
    }

    private ServerInfo getServerInfo(String data) {
        String[] serverInfoData = data.split(":");
        if (serverInfoData.length != 2) {
            return null;
        }
        ServerInfo serverInfo = ServerInfo.builder()
                .host(serverInfoData[0])
                .port(Integer.valueOf(serverInfoData[1]))
                .build();
        return serverInfo;

    }


    public void removeServer(String data) {
        LoadBalanceStrategy strategy = channels.remove(data);
        loadBalanceStrategies.remove(strategy);
    }


    private int currentIndex = 0;

    public Channel getChannel() {

        long now = System.currentTimeMillis();
        LoadBalanceStrategy strategy = loadBalanceStrategies.stream().sorted(Comparator.comparingLong(LoadBalanceStrategy::getLastRequestTime)).findFirst().get();
        if (!(strategy.getLastRequestTime() + 5000 > now)) {
            // 轮询策略获取
            strategy = loadBalanceStrategies.get(currentIndex);
            currentIndex++;
            if (currentIndex >= loadBalanceStrategies.size()) {
                currentIndex = 0;
            }
        }
        strategy.setLastRequestTime(now);
        log.info("当前访问服务器：{}", strategy.host);

        return strategy.getChannel();

    }


    public void close() {

    }


    @Builder
    static class ServerInfo {
        private String host;
        private int port;
    }
}
