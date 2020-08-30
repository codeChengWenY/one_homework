package cn.coder.pojo;

/**
 * @ClassName PropertyValue
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 15:58
 * @Version V1.0
 **/
public class PropertyValue {

    private String name;
    // 属性类型
    private Class<?> type;
    // 依赖标识
    private boolean refFlag;
    // bean名称
    private String beanName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isRefFlag() {
        return refFlag;
    }

    public void setRefFlag(boolean refFlag) {
        this.refFlag = refFlag;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
