package com.lagou.proxy;


import com.lagou.client.NettyClient;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static <T> T create(Class<T> interfaceClass, NettyClient nettyClient) throws Exception {
        RpcClientDynamicProxy<T> proxy = new RpcClientDynamicProxy<>(interfaceClass);
        proxy.setNettyClient(nettyClient);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[] {interfaceClass}, proxy);
    }
}