package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * Description: Interface of a container which is
 * able to autowire dependency.
 *
 * @author ShaoJiale
 * date 2020/1/10
 * @see ConfigurableBeanFactory
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    public Object resolveDependency(DependencyDescriptor descriptor);
}
