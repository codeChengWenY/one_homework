package cn.codercheng.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName CompleteableFuture
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-14 16:40
 * @Version V1.0
 **/
public class CompleteableFutureDemo {


//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//
//        CompletableFuture<Object> future = new CompletableFuture<>();
//
//      new Thread(()->{
//          future.complete("hello world");
//      }).start();
//
//
//        Object result = future.get();
//
//
//        System.out.println(result);
//    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

              CompletableFuture<Object> future =CompletableFuture.supplyAsync(new Supplier<Object>() {
                  @Override
                  public Object get() {
                      return "这是结果";
                  }
              });
        Object o = future.get();

       // System.out.println("任务执行结果: "+o);


        CompletableFuture<Object> future1 = CompletableFuture.supplyAsync(new Supplier<String>() {


            @Override
            public String get() {
                return "hello world";
            }
        }).thenApply(new Function<String, Object>() {


            @Override
            public Object apply(String s) {
                return s.length();
            }
        }).thenApply(new Function<Object, Object>() {


            @Override
            public Object apply(Object o) {
                return o+"aaaaa";
            }
        });




        System.out.println(future1.get());

    }
}
