package util;

/**
 * @ClassName ClassLoaderChecker
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-27 17:50
 * @Version V1.0
 **/
public class ClassLoaderChecker {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        MyClassLoader myClassLoader = new MyClassLoader("C:\\Users\\Lenovo\\Desktop\\", "random");
//        Class c = myClassLoader.findClass("");
//        System.out.println("ClassLoader:" + c.getClassLoader());
//        Hello1 instance = (Hello1) c.newInstance();
//
//        instance.hello();

        String a ="\\";

        String replace = a.replace("\\", "/");
        System.out.println(replace);

    }
}
