package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/**
 * @author ShaoJiale
 * @date 2019/12/10
 * @description Spring 容器接口,定义容器行为
 */
public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
