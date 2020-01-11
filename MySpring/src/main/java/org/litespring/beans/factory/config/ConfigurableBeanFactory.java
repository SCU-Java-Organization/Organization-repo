package org.litespring.beans.factory.config;

import java.util.List;

/**
 * Description: A sub-interface of BeanFactory
 * We can set/get a class loader for a container(factory/applicationContext)
 * by implementing this interface, or we can only use class utils to set a
 * default class loader for beans with hard coding. Such as:
 * this.classLoader = ClassUtils.getDefaultClassLoader();
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see org.litespring.util.ClassUtils
 * @see AutowireCapableBeanFactory
 * @see org.litespring.context.ApplicationContext
 * @see org.litespring.beans.factory.support.DefaultBeanFactory
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessor();
}
