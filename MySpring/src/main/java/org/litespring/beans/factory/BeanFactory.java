package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/**
 * description: Container of my Spring, define some behavior
 * @author ShaoJiale
 * date 2019/12/10
 */
public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
