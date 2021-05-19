/**
 * @ClassName TestThread
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-12 17:22
 * @Version V1.0
 **/
public class TestThread {


    public static void main(String[] args) {

        System.out.println(1);

        new Thread(()->{
            System.out.println(2);
        }).start();

    }
}
