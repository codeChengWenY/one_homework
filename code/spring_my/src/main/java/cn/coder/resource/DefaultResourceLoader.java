package cn.coder.resource;

import cn.coder.anno.Autowired;
import cn.coder.anno.Service;
import cn.coder.pojo.BeanDefinition;
import cn.coder.pojo.PropertyValue;
import cn.coder.utils.PackageScanUtils;
import cn.coder.utils.ResolveUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultResourceLoader
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 15:53
 * @Version V1.0
 **/
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public List<BeanDefinition> reader(String basePackage, String excludePackage) throws ClassNotFoundException {
        List<String> classNameList = PackageScanUtils.doScanner(basePackage, excludePackage);
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        for (String className : classNameList) {
            Class<?> clazz = Class.forName(className);
            // 是否使用了Service注解
            if (clazz.isAnnotationPresent(Service.class)) {
                // 获取该注解的值
                Service service = clazz.getAnnotation(Service.class);
                String beanName = service.value();
                if ("".equals(service.value())) {
                     beanName = ResolveUtils.lowerFirstCase(clazz.getName());
                }
                parseBeanDefinition(beanDefinitions, className, clazz, beanName);
            }
        }
        return beanDefinitions;
    }


    /**
     * 将类文件封装为BeanDefinition
     *
     * @param beanDefinitions beanDefinitions集合
     * @param className       全限定类名
     * @param clazz           class类
     * @param beanName        bean名称
     */
    private void parseBeanDefinition(List<BeanDefinition> beanDefinitions, String className, Class<?> clazz, String beanName) {
        BeanDefinition beanDefinition = new BeanDefinition();
        List<PropertyValue> propertyValueList = new ArrayList<>();
        beanDefinition.setClassName(className);
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setBeanName(beanName);
        // 获取类属性值
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 获取Autowired注解的属性
            Autowired annotation = field.getAnnotation(Autowired.class);
            PropertyValue propertyValue = new PropertyValue();
            propertyValue.setName(field.getName());
            propertyValue.setType(field.getType());
            if (annotation != null) {
                propertyValue.setRefFlag(true);
                // 获取注解的值
                String value = annotation.value();
                if ("".equals(value)) {
                   value = ResolveUtils.lowerFirstCase(field.getType().getSimpleName());
                }
                propertyValue.setBeanName(value);
            } else {
                propertyValue.setRefFlag(false);
            }
            propertyValueList.add(propertyValue);
        }
        // 装配属性
        beanDefinition.setPropertyValues(propertyValueList);
        beanDefinitions.add(beanDefinition);
    }
}
