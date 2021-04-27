package cn.codercheng.demo;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolExecutorDemo
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-14 10:32
 * @Version V1.0
 **/
public class ThreadPoolExecutorDemo {


    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,300, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>()
        );

        threadPoolExecutor.submit(new Mythread("0"));

        Thread.sleep(1);
        threadPoolExecutor.shutdown();

        System.out.println("结束了");
    }
}

class   Mythread extends  Thread{

    String name;


    public  Mythread(String name){

        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100 && !Thread.interrupted(); i++) {
            Thread.yield();
            System.out.println("task "+name +" is running round"+i);
        }
    }




}


class   Mythread2 extends  Thread {

    String name;

    public Mythread2(String name) {

        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100 && !Thread.interrupted(); i++) {
            Thread.yield();
            System.out.println("task " + name + " is running round" + i);
        }
    }
}