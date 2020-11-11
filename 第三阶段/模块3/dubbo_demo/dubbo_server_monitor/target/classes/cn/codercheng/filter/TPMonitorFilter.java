package cn.codercheng.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName TPMonitorFilter
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-11 12:07
 * @Version V1.0
 **/
@Slf4j
@Activate(group = {CommonConstants.PROVIDER})
public class TPMonitorFilter implements Filter {

    //
    private  final TPMonitor tpMonitor = new TPMonitor();

    public TPMonitorFilter() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(tpMonitor);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("请求过来了----------");
        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long useTime = System.currentTimeMillis() - start;
        tpMonitor.request(System.currentTimeMillis(), useTime);
        return result;

    }


}
