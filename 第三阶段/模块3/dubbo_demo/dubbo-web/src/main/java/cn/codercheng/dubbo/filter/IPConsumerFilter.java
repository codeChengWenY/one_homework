package cn.codercheng.dubbo.filter;

import cn.codercheng.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName IPConsumerFilter
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-11 10:41
 * @Version V1.0
 **/

@Slf4j
@Activate(group = {CommonConstants.CONSUMER})
public class IPConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            String ipAddress = IPUtil.getIpAddress(requestAttributes.getRequest());
            RpcContext.getContext().setAttachment("IP", ipAddress);
            log.info("put IP:{}",ipAddress);
        }

        return invoker.invoke(invocation);
    }
}