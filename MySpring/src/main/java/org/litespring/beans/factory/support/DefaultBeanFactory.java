package org.litespring.beans.factory.support;

import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionRegistry;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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

    private Object singletonLock;

    public DefaultBeanFactory() {
        singletonLock = new Object();
    }

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
     * @see #createBean(BeanDefinition)
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);

        if(bd == null)
            throw new BeanCreationException("Bean Definition does not exist - ['" + beanID + "']");

        // if the bean is singleton
        // use double check lock to create bean
        if(bd.isSingleton()){
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                synchronized (singletonLock){
                    if((bean = this.getSingleton(beanID)) == null){
                        bean = createBean(bd);
                        this.registerSingleton(beanID, bean);
                    }
                }
            }
            return bean;
        }

        // prototype, just create another one and return it 
        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd){
        // create instance
        Object bean = instantiateBean(bd);
        
        // set properties for the instance
        populateBean(bd, bean);
        
        return bean;
    }



    /**
     * Create an instance of a specific bean
     * @param bd bean definition
     * @return instance of the bean
     */
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader loader = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            Class<?> clazz = loader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e){
            throw new BeanCreationException(beanClassName, "create bean with name of " + beanClassName + " failed", e);
        }
    }

    protected void populateBean(BeanDefinition bd, Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();

        if(pvs == null || pvs.isEmpty())
            return;

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();   // RuntimeBean or TypedString
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for(PropertyDescriptor pd : pds){
                    if(pd.getName().equals(propertyName)){
                        pd.getWriteMethod().invoke(bean, resolvedValue);
                        break;
                    }
                }
            }
        } catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" +
                    bd.getBeanClassName() + "]");
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
