package org.litespring.beans.factory.config;

/**
 * Description: A interface for singleton bean
 * You should implement this class for singleton bean.
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see org.litespring.beans.factory.support.DefaultSingletonBeanRegistry
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
