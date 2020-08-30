package cn.coder.factory;

import cn.coder.anno.Transactional;
import cn.coder.pojo.BeanDefinition;
import cn.coder.pojo.PropertyValue;
import cn.coder.resource.DefaultResourceLoader;
import cn.coder.resource.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultBeanFactory
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 16:02
 * @Version V1.0
 **/
public class DefaultBeanFactory implements BeanFactory {


    // 一级缓存
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 二级缓存
    private Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();
    // 三级缓存：用于解决循环依赖问题
    private Map<String, Object> singletonFactories = new ConcurrentHashMap<>();
    // BeanDefinition集合
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    private Properties properties = new Properties();



    // 配置文件路径 如：applicationContext.xml
    private String configLocation;


    private final String BASE_PACKAGE = "base-package";
    private final String EXCLUDE_PACKAGE = "exclude-package";


    public DefaultBeanFactory(String configLocation) throws DocumentException {
        this.configLocation = configLocation;
        // 加载配置文件并解析
        loadConfigLocation(configLocation);
        // 对bean进行实例化、属性填充等一系列操作
        refresh();
    }


    public void loadConfigLocation(String configLocation) throws DocumentException {
        // 加载配置文件为输入流
        InputStream inputStream = DefaultBeanFactory.class.getClassLoader().getResourceAsStream(configLocation);
        // 使用dom4j技术解析
        Document root = new SAXReader().read(inputStream);
        Element rootElement = root.getRootElement();
        List<Element> list = rootElement.selectNodes("//component-scan");
        for (Element element : list) {
            String basePackage = element.attributeValue("base-package");
            String excludePackage = element.attributeValue("exclude-package");
            properties.setProperty(BASE_PACKAGE, basePackage);
            properties.setProperty(EXCLUDE_PACKAGE, excludePackage);

        }
    }

    public void refresh() {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            List<BeanDefinition> beanDefinitions = resourceLoader.reader(properties.getProperty(BASE_PACKAGE), properties.getProperty(EXCLUDE_PACKAGE));
            // 将bean注册为beanDefinition对象
           registerBeanDefinitions(beanDefinitions);
            // 注册bean
            registerBean(beanDefinitions);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将bean注册为beanDefinition对象
     *
     * @param beanDefinitions beanDefinitions集合
     */
    private void registerBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
        }
    }


    /**
     * 注册bean
     *
     * @param beanDefinitions beanDefinitions集合
     */
    private void registerBean(List<BeanDefinition> beanDefinitions) throws IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            getBean(beanDefinition.getBeanName());
        }
    }

    /**
     * 获取bean
     *
     * @param beanName bean的名字
     * @return {@link Object}
     */
    @Override
    public Object getBean(String beanName) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return doGetBean(beanName);
    }


    private Object doGetBean(String beanName) throws IllegalAccessException, InstantiationException,
            NoSuchFieldException {
        // 首先从缓存中获取
        Object singletonBean = getSingleton(beanName);
        if (singletonBean != null) {
            return singletonBean;
        }

        singletonBean = getSingleton(beanName,  () ->{
            return doCreateBean(beanName);
        });
        return singletonBean;

    }


    private Object getSingleton(String beanName, ObjectFactory<?> objectFactory) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                //从一级缓存中取出bean并将bean放入单例池中
                singletonObject = objectFactory.getObject();
                addSingleton(beanName, singletonObject);
            }
            return singletonObject;
        }
    }


    private Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null) {
                    singletonObject = this.singletonFactories.get(beanName);
                    if (singletonObject != null) {
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    /**
     * 添加bean到单例池
     * @param beanName bean的名字
     * @param singletonObject 单例对象
     */
    private void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects){
            this.singletonObjects.put(beanName, singletonObject);
            this.earlySingletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
        }
    }

    private Object doCreateBean(String beanName) throws InstantiationException, IllegalAccessException,
            NoSuchFieldException {
        // 创建 Bean 实例，仅仅调用构造方法，但是尚未设置属性
        Object exposedBean = instanceBean(beanName);
        // 放入三级缓存
        addSingletonFactory(beanName, exposedBean);
        // Bean属性填充
        populateBean(beanName, exposedBean);
        // 调用初始化方法
        exposedBean = initializeBean(beanName, exposedBean);
        return exposedBean;
    }


    /**
     * 填充Bean
     *
     * @param beanName    bean的名字
     * @param exposedBean 暴露的bean
     * @throws NoSuchFieldException   没有这样的磁场异常
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 实例化异常
     */
    private void populateBean(String beanName, Object exposedBean) throws IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition != null && !beanDefinition.getPropertyValues().isEmpty()) {
            List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues) {
                if (propertyValue.isRefFlag()) {
                    // 根据依赖的beanName获取对应的bean
                    Object bean = this.getBean(propertyValue.getBeanName());
                    // 获取暴露的bean的属性
                    Field field = exposedBean.getClass().getDeclaredField(propertyValue.getName());
                    field.setAccessible(true);
                    // 装配属性
                    field.set(exposedBean, bean);
                }
            }
        }
    }

    /**
     * 将bean提前暴露出来
     *
     * @param beanName    bean的名字
     * @param exposedBean 暴露的bean
     */
    private void addSingletonFactory(String beanName, Object exposedBean) {
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, exposedBean);
            }
        }
    }


    /**
     * 实例化bean
     *
     * @param beanName bean的名字
     * @return {@link Object}* @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 实例化异常
     */
    private Object instanceBean(String beanName) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition != null) {
            Class<?> beanClass = beanDefinition.getBeanClass();
            return beanClass.newInstance();
        }
        return null;
    }


    /**
     * bean扩展
     *
     * @param beanName
     * @param exposedBean
     * @return {@link Object}
     */
    private Object initializeBean(String beanName, Object exposedBean) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition != null) {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (isTransactionalAnno(beanClass)) {
                //判断氮当前是否实现了接口 实现接口用cglib
                for(Type t : beanClass.getGenericInterfaces()) {
                    if(Objects.nonNull(t)){
                        return ProxyFactory.getInstance().getCglibProxy(exposedBean);
                    }
                }
                // 返回代理类：添加事务逻辑
                return ProxyFactory.getInstance().getJdkProxy(exposedBean);
            }
        }
        return exposedBean;
    }


    /**
     * 是否包含事务注解
     *
     * @param beanClass bean类
     * @return boolean
     */
    private boolean isTransactionalAnno(Class<?> beanClass) {
        Transactional transactional = beanClass.getAnnotation(Transactional.class);
        if (transactional != null) return true;

        // 获取方法
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            // 获取有Transactional注解的方法
            Transactional methodTransactional = beanClass.getAnnotation(Transactional.class);
            if (methodTransactional != null) return true;
        }
        return false;
    }

}
