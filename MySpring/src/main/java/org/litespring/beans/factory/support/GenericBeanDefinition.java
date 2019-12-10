package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * description: A generic implementation of BeanDefinition
 * @author ShaoJiale
 * date 2019/12/10
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
