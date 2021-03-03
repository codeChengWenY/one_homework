package cn.codercheng.rocketmq_demo;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName LongAdderAndAtomicLongTest
 * @Description:
 * @Author CoderCheng
 * @Date 2021-02-26 21:38
 * @Version V1.0
 **/
public class LongAdderAndAtomicLongTest {

    private static AtomicLong atomicLong = new AtomicLong();


    public static HashMap<String, Long> orderMap = new HashMap<String, Long>();



    public static void main(String[] args) {
        //AtomicLong 线程池
       // ExecutorService executorService = Executors.newFixedThreadPool(20);

         ExecutorService executorService = Executors.newCachedThreadPool();

        long beginAtomicLongMills = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                atomicLong.getAndIncrement();
                orderMap.put( UUID.randomUUID().toString(),atomicLong.getAndIncrement());
            });
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("执行：" + atomicLong.get() + "AtomicLong多线程累加耗时：" + (System.currentTimeMillis() - beginAtomicLongMills));
        System.out.println("map长度"+orderMap.size());


    }
}
