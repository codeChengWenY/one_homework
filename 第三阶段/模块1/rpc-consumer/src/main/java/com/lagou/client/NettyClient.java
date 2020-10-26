package com.lagou.client;

import com.lagou.handler.UserClientHandler;
import com.lagou.pojo.RPCResponse;
import com.lagou.rpcRequest.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Slf4j
@Component
public class NettyClient {


    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private UserClientHandler clientHandler;

    /**
     * 发送消息
     *
     * @param request
     * @return
     */
    public RPCResponse send(final RpcRequest request) {
        try {

            clientHandler.futureMap.putIfAbsent(request.getRequestId(), new DefaultFuture());
            connectManager.getChannel().writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientHandler.getRPCResponse(request.getRequestId());
    }

    @PreDestroy
    public void close() {
        connectManager.close();
    }
}