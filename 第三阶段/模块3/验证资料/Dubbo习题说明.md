                                                Dubbo编程题      



###  项目结构说明

- dubbo-web      测试Consumer
- dubbo-server-monitor   TP90  TP99性能监听Filter
- dubbo_spi_filter   IP透传Filter
- service-api    通用RPC接口
- service-provider           服务端
- service-provider-1       服务端1号

​                         

###         编程题一：将Web请求IP透传到Dubbo服务中

 先看 实现效果  上图  

![image-20201111171252402](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/171252-508620.png)

![image-20201111171314121](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/171316-28018.png)

 ![image-20201111171343847](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/171344-943669.png)   实现步骤：

1、service-api 中定义RPC测试接口 HelloService#sayHello方法

2、采用不同配置端口实现启动两个Dubbo服务端

3、dubbo_spi_filter  利用dubbo的spi机制定义Filter，从RPCContext中读取Consumer中传递的IP，两个Dubbo服务端皆依赖该模块 TransportIPFilter

服务端

```java
   @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
            String ip = RpcContext.getContext().getAttachment("IP");
            if(StringUtils.isNoneBlank(ip)){
                log.info("当前请求IP：{}",ip);
            }
            // 执行方法
            return  invoker.invoke(invocation);

    }
```

客户端![image-20201111171512526](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/171519-819862.png)



###             编程题二：简易版Dubbo方法级性能监控

#### 测试结果 （5秒钟打印一次记录）

![image-20201111172954789](https://gitee.com/adc123321/blog_img/raw/master/image/202011/11/172955-469131.png)



#### 实现步骤

1、 server-api定义了RPC接口，接口中定义了三个方法

- HelloService#sayHello
- HelloService#sayHello1
- HelloService#sayHello2

2 、 server-provider 中提供Dubbo服务实现api中定义的HelloService接口

3 、 dubbo-server-monitor中定义Filter拦截RPC请求并将请求时间（KEY）和请求耗时（VALUE）存储到Map中 核心代码： TPMonitor

```java
@Slf4j
public class TPMonitor implements  Runnable{

    public static final long VALID_TIME = 1000*60;
    private Map<Long, Long> useTimeMaps= new ConcurrentHashMap<>();

    // 对外暴露调用接口存储耗时
    public void request(Long requestTime,Long useTime){
        useTimeMaps.put(requestTime,useTime);
    }

    // 清除过期数据
    private void clearOld(){
        long valid = System.currentTimeMillis()-VALID_TIME;
        Set<Long> requestTimes = useTimeMaps.keySet();
        for (Long r:requestTimes){
            if(r<valid){
                useTimeMaps.remove(r);
            }
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(5000);
                // 执行前先清除过期数据
                 clearOld();
                final Map<Long,Long> temp = Maps.newHashMap(useTimeMaps);
                int total = temp.size();
                if (total == 0){
                    continue;
                }
                int tp90 = (int) (total*0.9);
                int tp99 = (int) (total*0.99);
                final Collection<Long> requestTimes = temp.values();

                Object[] sortedRequestTimes = requestTimes.stream().sorted().toArray();
                log.info("=====>>>>> tp90:{}ms",sortedRequestTimes[tp90]);
                log.info("=====>>>>> tp99:{}ms",sortedRequestTimes[tp99]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
```

TPMonitorFilter

```java

private  final TPMonitor tpMonitor = new TPMonitor();

//注意此构造必须是公有的 不然 拦截器会失效
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
```