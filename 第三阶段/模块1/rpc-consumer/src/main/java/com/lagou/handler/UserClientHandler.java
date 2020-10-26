package com.lagou.handler;

import com.lagou.client.DefaultFuture;
import com.lagou.pojo.RPCResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义事件处理器
 */
@Component
@ChannelHandler.Sharable
public class UserClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 使用Map维护请求对象ID与响应结果Future的映射关系
     */
    public final Map<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();


    //3.实现channelRead 当我们读到服务器数据,该方法自动执行
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RPCResponse) {
            //获取响应对象
            RPCResponse response = (RPCResponse) msg;
            DefaultFuture defaultFuture = futureMap.get(response.getRequestId());
            //将结果写入DefaultFuture
            defaultFuture.setResponse(response);
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 获取响应结果
     *
     * @param requsetId
     * @return
     */
    public RPCResponse getRPCResponse(String requsetId) {
        try {
            DefaultFuture future = futureMap.get(requsetId);
            return future.getRpcResponse(5000);
        } finally {
            //获取成功以后，从map中移除
            futureMap.remove(requsetId);
        }
    }
}
