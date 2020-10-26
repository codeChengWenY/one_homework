package com.lagou.proxy;

import com.lagou.client.NettyClient;
import com.lagou.pojo.RPCResponse;
import com.lagou.rpcRequest.RpcRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public class RpcClientDynamicProxy<T> implements InvocationHandler {

    @Setter
    private NettyClient nettyClient;
    private Class<T> clazz;
    public RpcClientDynamicProxy(Class<T> clazz) throws Exception {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String requestId = UUID.randomUUID().toString();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        Class<?>[] parameterTypes = method.getParameterTypes();
        RpcRequest request = new RpcRequest();
        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(args);
        System.out.println("======>>>>params:"+args[0]);
        log.info("请求内容: {}",request);
        RPCResponse send = nettyClient.send(request);
        log.info("请求调用返回结果：{}", send.getData());

        return send.getData();
    }
}