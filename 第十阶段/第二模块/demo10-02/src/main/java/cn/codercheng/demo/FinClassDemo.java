package cn.codercheng.demo;

/**
 * @ClassName FinClassDemo
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-14 17:18
 * @Version V1.0
 **/
public class FinClassDemo {



    public static void main(String[] args) {

        FinClassDemo.TwoClass twoClass = new FinClassDemo.TwoClass();


    }




       private static   final  class  TwoClass{

        private TwoClass(){
            System.out.println("私有构造");
        }

//        int num =0;
//
//        public int getNum() {
//            return num;
//        }
//
//        public void setNum(int num) {
//            this.num = num;
//        }
    }

}
