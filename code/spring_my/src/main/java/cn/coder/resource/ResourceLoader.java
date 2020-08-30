package cn.coder.resource;

import cn.coder.pojo.BeanDefinition;

import java.util.List;

/**
 * @ClassName ResourceLoader
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 15:50
 * @Version V1.0
 **/
public interface  ResourceLoader {


    List<BeanDefinition> reader(String basePackage, String excludePackage) throws ClassNotFoundException;

}
