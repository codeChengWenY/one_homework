package com.lagou.client;


import com.lagou.pojo.RPCResponse;

public class DefaultFuture {
    private RPCResponse rpcResponse;
    private volatile boolean isSucceed = false;
    private final Object object = new Object();

    public RPCResponse getRpcResponse(int timeout) {
        synchronized (object) {
            while (!isSucceed) {
                try {
                    object.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        }
    }

    public void setResponse(RPCResponse response) {
        if (isSucceed) {
            return;
        }
        synchronized (object) {
            this.rpcResponse = response;
            this.isSucceed = true;
            object.notify();
        }
    }
}