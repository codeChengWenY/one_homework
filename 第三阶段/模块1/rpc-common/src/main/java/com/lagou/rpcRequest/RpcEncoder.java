package com.lagou.rpcRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName RpcEncoder
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-18 15:29
 * @Version V1.0
 **/
public class RpcEncoder  extends MessageToByteEncoder {


    private Class<?> clazz;

    private Serializer serializer;



    public RpcEncoder(Class<?> clazz, Serializer serializer) {

        this.clazz = clazz;

        this.serializer = serializer;

    }



    @Override

    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {

        if (clazz != null && clazz.isInstance(msg)) {

            byte[] bytes = serializer.serialize(msg);

            byteBuf.writeInt(bytes.length);

            byteBuf.writeBytes(bytes);

        }

    }
}
