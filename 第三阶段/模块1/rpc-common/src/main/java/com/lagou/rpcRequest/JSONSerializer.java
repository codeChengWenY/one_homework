package com.lagou.rpcRequest;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName JSONSerializer
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-18 15:31
 * @Version V1.0
 **/
public class JSONSerializer implements Serializer {


    @Override

    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);

    }


    @Override

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);

    }
}
