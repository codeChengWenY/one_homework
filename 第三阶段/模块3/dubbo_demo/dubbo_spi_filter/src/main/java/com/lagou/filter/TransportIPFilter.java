package com.lagou.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;


@Slf4j
@Activate(group = {CommonConstants.PROVIDER,CommonConstants.CONSUMER})
public class TransportIPFilter   implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
            String ip = RpcContext.getContext().getAttachment("IP");
            if(StringUtils.isNoneBlank(ip)){
                log.info("当前请求IP：{}",ip);
            }
            // 执行方法
            return  invoker.invoke(invocation);

    }
}
