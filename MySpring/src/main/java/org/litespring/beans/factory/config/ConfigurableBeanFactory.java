package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/12
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);
    ClassLoader getBeanClassLoader();
}
