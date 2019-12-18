package org.litespring.beans.factory.config;

/**
 * Description: Describe the ref tag
 * If the ref references to a bean, then use this.
 * Another condition is that the ref is a String.
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see TypedStringValue
 */
public class RuntimeBeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
