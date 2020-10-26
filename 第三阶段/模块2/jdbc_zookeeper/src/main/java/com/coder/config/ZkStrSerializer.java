package com.coder.config;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * @ClassName ZkStrSerializer
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-22 16:41
 * @Version V1.0
 **/
public class ZkStrSerializer  implements ZkSerializer {



    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        return String.valueOf(o).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes);
    }
}
