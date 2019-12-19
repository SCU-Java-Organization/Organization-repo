package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * Description: Resolve property value
 * As we can see, the value in the 'property' tag
 * can be a bean(ref) or a String(value). If it's a bean,
 * users have to define it in the XML config file, which means
 * this bean is loaded when the method loadBeanDefinition()
 * is called. So the only thing we need to do is get the
 * 'ref' bean from the factory.
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see org.litespring.beans.factory.xml.XmlBeanDefinitionReader
 * @see DefaultBeanFactory
 */
public class BeanDefinitionValueResolver {

    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Resolve 'ref' or 'value' in the property tag
     * The only thing we need to do is get it from the
     * factory because we've defined it in the XML config file.
     *
     * @param value a RuntimeBean or a String
     * @return a bean or a String
     */
    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO more types...
            throw new RuntimeException("the value " + value + " has not been implemented");
        }
    }
}
