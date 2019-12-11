package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanDefinitionRegistry;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Description: Default implementation of BeanFactory
 * This is a default bean-container of lite-spring
 * @see BeanFactory
 * @author ShaoJiale
 * date 2019/12/10
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {}

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition definition) {
        this.beanDefinitionMap.put(beanID, definition);
    }

    /**
     * Get bean definition from container
     * @param beanID bean id
     * @return BeanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * Get instance of bean
     * @param beanID bean id
     * @return instance
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition definition = this.getBeanDefinition(beanID);

        if(definition == null)
            throw new BeanCreationException("Bean Definition does not exist!");

        if(definition.isSingleton()){
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                bean = createBean(definition);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(definition);
    }

    private Object createBean(BeanDefinition definition) {
        ClassLoader loader = this.getBeanClassLoader();
        String beanClassName = definition.getBeanClassName();

        try {
            Class<?> clazz = loader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e){
            throw new BeanCreationException(beanClassName, "create bean with name of " + beanClassName + " failed", e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
