package cn.codercheng.demo;

public class Test2 {

    static Object object2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            synchronized (object2){
                System.out.println("大雄 获得了共享变量object2的锁");
                try {
                    object2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("大雄 被唤醒，重新获得了锁，继续向下执行");
            }
        });

        //位于大雄小面，极大可能晚于大雄执行，主线程优先执行大雄，再执行小夫
        Thread t2 = new Thread(()->{
            synchronized (object2){
                System.out.println("小夫 获得了共享变量object2的锁");
                try {
                    object2.wait();
                } catch (InterruptedException e) {
                    System.out.println("并没有抛出异常");
                    e.printStackTrace();
                }
                System.out.println("小夫 被唤醒，重新获得了锁，继续向下执行");
            }
        });

        //让线程3随机唤醒一个，这样另一个其实还是处于阻塞状态，除非notify或者interrupt或者处于假唤醒状态
        Thread t3 = new Thread(()->{
            synchronized (object2){
                System.out.println("胖虎获得了共享变量object2的锁，即将随机唤醒");
                object2.notifyAll();

            }
            System.out.println("t3执行完了 ");

        });

        t1.start();
        t2.start();
        //模拟顺利让大雄和小夫顺序获得锁，而不是胖虎在小夫之前获得锁
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();

        try {
            t1.join();  //主线程在调用这个方法后阻塞，直到线程1执行完毕，才继续向下执行，join后期被countDonwLatch代替
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程1，2，3执行结束，主线程退出");

    }

}