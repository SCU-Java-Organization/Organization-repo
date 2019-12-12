package org.litespring.beans.factory;

/**
 * Description: Container of my Spring
 * Define some behaviors of container.
 *
 * @see org.litespring.beans.factory.support.DefaultBeanFactory
 * @see org.litespring.beans.factory.config.ConfigurableBeanFactory
 * @author ShaoJiale
 * date 2019/12/10
 */
public interface BeanFactory {
    Object getBean(String beanID);
}
