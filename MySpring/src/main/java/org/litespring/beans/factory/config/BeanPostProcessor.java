package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
