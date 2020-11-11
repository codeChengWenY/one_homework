package cn.codercheng.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TPMonitor
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-11 12:13
 * @Version V1.0
 **/


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
                final Map<Long,Long> temp = Maps.newHashMap(useTimeMaps);
                int total = temp.size();
                if (total == 0){
                    continue;
                }
                clearOld();
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
