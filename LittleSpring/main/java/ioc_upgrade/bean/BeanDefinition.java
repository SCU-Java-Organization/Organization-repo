package ioc_upgrade.bean;

import ioc_upgrade.property.PropertyValues;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function Bean的信息实体类
 */
public class BeanDefinition {
    private Object bean;
    private Class beanClass;
    private String className;
    private PropertyValues propertyValues = new PropertyValues();

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getClassName() {
        return className;
    }

    /**
     * @function 更改bean的类名，同时更改bean对应的Class类
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
        try {
            this.beanClass = Class.forName(className);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
