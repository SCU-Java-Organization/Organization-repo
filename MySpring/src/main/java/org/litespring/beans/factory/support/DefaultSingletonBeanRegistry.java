package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: A default singleton bean registry
 * This is a bean instance container, which contains bean name and instance.
 * Another container is the definition map in the factories, which contains
 * bean id and bean definition. And that's why we should use it in a factory.
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see DefaultBeanFactory
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");

        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null)
            throw new IllegalArgumentException("Could not register object [" + singletonObject +
                    "] with bean name of '" + beanName + "': there is already object [" + oldObject + "]");

        this.singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
