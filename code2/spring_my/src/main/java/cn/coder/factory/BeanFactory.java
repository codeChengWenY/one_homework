package cn.coder.factory;

/**
 * @author 应癫
 *
 * 工厂类，生产对象（使用反射技术）
 */
public interface BeanFactory {




    // 根据bean名称获取实例对象
    Object getBean(String beanName) throws InstantiationException, IllegalAccessException, NoSuchFieldException;;

}
