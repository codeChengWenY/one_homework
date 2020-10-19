package com.lagou.handler;

import com.lagou.rpcRequest.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;

/**
 * 自定义的业务处理器
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {


    private ApplicationContext applicationContext;

    public UserServiceHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    //当客户端读取数据时,该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("aaaa");

        //校验发送过来的数据是否是RpcRequest对象
        if(msg instanceof RpcRequest){
            RpcRequest rpcRequest = (RpcRequest)msg;
            //使用Class.forName进行加载Class文件
            Class<?> clazz = Class.forName(rpcRequest.getClassName());
            Object serviceBean = applicationContext.getBean(clazz);
            Class<?> serviceClass = serviceBean.getClass();
            String methodName = rpcRequest.getMethodName();

            Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
            Object[] parameters = rpcRequest.getParameters();
            System.out.println("客户端传递过来参数parameters:"+parameters[0]);
            //使用CGLIB动态代理
            FastClass fastClass = FastClass.create(serviceClass);
            FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
            //开始调用CGLIB动态代理执行服务端方法
            Object result = fastMethod.invoke(serviceBean, parameters);
            //将执行结果写回客户端
            ctx.writeAndFlush(result.toString());
        }

    }
}
