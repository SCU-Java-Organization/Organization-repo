package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * Description: Resolve property value
 * As we can see, the 'ref' in the 'property' tag
 * is a bean or a String. If it's a bean, users have
 * to define it in the XML config file, which means
 * this bean is loaded when the method loadBeanDefinition()
 * is called. So the only thing we need to do is get the
 * 'ref' bean from the factory.
 *
 * @see org.litespring.beans.factory.xml.XmlBeanDefinitionReader
 * @see DefaultBeanFactory
 * @author ShaoJiale
 * date 2019/12/12
 */
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference ref = (RuntimeBeanReference)value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if(value instanceof TypedStringValue){
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO
            throw new RuntimeException("the value " + value + " has not been implemented");
        }
    }
}
