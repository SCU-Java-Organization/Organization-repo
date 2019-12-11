package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;

/**
 * Description: Register bean definitions to bean-container (factory)
 * @see org.litespring.beans.factory.BeanFactory
 * @see DefaultBeanFactory
 * @author ShaoJiale
 * date 2019/12/11
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition definition);
}
