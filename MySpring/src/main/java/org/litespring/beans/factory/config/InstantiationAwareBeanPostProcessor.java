package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2020/1/10
 * @see org.litespring.beans.factory.annotation.AutowireAnnotationProcessor
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}
