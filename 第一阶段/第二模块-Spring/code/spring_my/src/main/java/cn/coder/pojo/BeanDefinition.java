package cn.coder.pojo;


import java.util.List;

/**
 * @ClassName BeanDefinition
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 15:52
 * @Version V1.0
 **/
public class BeanDefinition {

    // bean名称
    private String beanName;
    // bean类
    private Class<?> beanClass;
    // 全限定类名
    private String className;
    // 属性值
    private List<PropertyValue> propertyValues;

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
