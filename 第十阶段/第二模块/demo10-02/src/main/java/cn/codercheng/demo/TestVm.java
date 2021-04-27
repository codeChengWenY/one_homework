package cn.codercheng.demo;

/**
 * @ClassName TestVm
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-15 17:10
 * @Version V1.0
 **/
public class TestVm {


    public static void main(String[] args) throws InterruptedException {



      // byte[] b=new byte[5*1024*1024];


        System.out.println(Runtime.getRuntime().maxMemory()/1024.0/1024+"M");
        System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024+"M");
        System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024+"M");


        Thread.sleep(30000000);

    }
}
