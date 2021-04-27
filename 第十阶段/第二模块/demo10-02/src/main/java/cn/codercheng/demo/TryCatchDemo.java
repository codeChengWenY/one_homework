package cn.codercheng.demo;

/**
 * @ClassName TryCatchDemo
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-14 14:00
 * @Version V1.0
 **/
public class TryCatchDemo {


    public static void main(String[] args) {


        try {
            int a=1/10;
        }catch (Exception e){

            e.printStackTrace();
        }


        System.out.println("catch 继续执行");
    }
}
