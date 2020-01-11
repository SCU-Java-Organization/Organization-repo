package org.litespring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionRegistry;
import org.litespring.beans.factory.config.BeanPostProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.beans.factory.exception.*;
import org.litespring.beans.BeanDefinition;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Description: Default implementation of BeanFactory
 * This is the default bean-container of lite-spring.
 * Factory contains <beanID, BeanDefinition>, and the
 * Registry contains <beanID, BeanInstance>
 *
 * @author ShaoJiale
 * date 2019/12/10
 * @see DefaultSingletonBeanRegistry
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ClassLoader beanClassLoader;

    private Object singletonLock;

    public DefaultBeanFactory() {
        singletonLock = new Object();
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessor() {
        return this.beanPostProcessors;
    }

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition definition) {
        this.beanDefinitionMap.put(beanID, definition);
    }

    /**
     * Get bean definition from container
     *
     * @param beanID bean id
     * @return BeanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * Get instance of bean
     *
     * @param beanID bean id
     * @return instance
     * @see #createBean(BeanDefinition)
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);

        if (bd == null)
            throw new BeanCreationException("Bean Definition does not exist - ['" + beanID + "']");

        // if the bean is singleton
        // use double check lock to create bean
        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                synchronized (singletonLock) {
                    if ((bean = this.getSingleton(beanID)) == null) {
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

    /**
     * Create a instance of a bean when it does not exist
     * We have to do 2 things:
     * 1.create a instance
     * 2.complete dependency injection
     *
     * @param bd Bean definition
     * @return A bean with dependency
     * @see #instantiateBean(BeanDefinition)
     * @see #populateBean(BeanDefinition, Object)
     */
    private Object createBean(BeanDefinition bd) {
        // create instance
        Object bean = instantiateBean(bd);

        // set properties for the instance
        populateBean(bd, bean);

        return bean;
    }


    /**
     * Create an instance of a specific bean
     *
     * @param bd bean definition
     * @return instance of the bean
     */
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader loader = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();

        if (bd.hasConstructorArgumentValues()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }
        
        try {
            Class<?> clazz = loader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException(beanClassName, "create bean with name of " + beanClassName + " failed", e);
        }
    }

    /**
     * Dependency injection
     * We need to use resolver to achieve DI
     *
     * @param bd   Bean definition
     * @param bean Bean ready to be injected
     * @see PropertyValue
     * @see BeanDefinitionValueResolver
     */
    protected void populateBean(BeanDefinition bd, Object bean) {

        for (BeanPostProcessor processor : this.getBeanPostProcessor()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean, bd.getID());
            }
        }

        // get property value list
        List<PropertyValue> pvs = bd.getPropertyValues();

        // no need to inject
        if (pvs == null || pvs.isEmpty())
            return;

        // We need to use resolver to judge if the property is a bean or String
        // Then use the converter to convert String into Number
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();

        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();   // original value is a RuntimeBean or a TypedString
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue); // actual value is a bean or a String

                // use BeanInfo to inject bean or value
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                // get descriptors of the current bean
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        // read type of the current field and convert it
                        Object convertValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" +
                    bd.getBeanClassName() + "]");
        }
    }

    /**
     * parse bean with common BeanUtils
     *
     * @param bd   bean definition
     * @param bean bean waiting for DI
     */
    public void populateBeanUseCommonBeanUtils(BeanDefinition bd, Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();

        if (pvs == null || pvs.isEmpty())
            return;

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();

                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanUtils.setProperty(bean, propertyName, resolvedValue);
            }
        } catch (Exception e) {
            throw new BeanCreationException("Populate bean property failed for [" +
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

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();

        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            // To make sure the bean has a Class
            resolveBeanClass(bd);

            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass))
                return this.getBean(bd.getID());
        }

        return null;
    }

    public void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass())
            return;
        try {
            bd.resolveBeanClass(this.getBeanClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("cannot load class: " + bd.getBeanClassName());
        }
    }
}
