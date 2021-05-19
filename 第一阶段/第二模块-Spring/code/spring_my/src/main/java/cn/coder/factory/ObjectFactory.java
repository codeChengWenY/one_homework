package cn.coder.factory;

/**
 * @ClassName ObjectFactory
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 16:18
 * @Version V1.0
 **/
@FunctionalInterface
public interface ObjectFactory<T> {


    Object getObject() throws InstantiationException, IllegalAccessException, NoSuchFieldException;

}
