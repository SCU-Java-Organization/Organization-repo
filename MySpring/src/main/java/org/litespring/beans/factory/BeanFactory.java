package org.litespring.beans.factory;

/**
 * Description: Container of my Spring
 * Define some behaviors of container.
 *
 * @author ShaoJiale
 * date 2019/12/10
 * @see org.litespring.beans.factory.support.DefaultBeanFactory
 * @see org.litespring.beans.factory.config.ConfigurableBeanFactory
 */
public interface BeanFactory {
    Object getBean(String beanID);
}
