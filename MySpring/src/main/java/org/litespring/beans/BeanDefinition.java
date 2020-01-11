package org.litespring.beans;

import java.util.List;

/**
 * Description: definition of bean
 * We use this class to describe the bean tag, including:
 * 1.bean id
 * 2.bean class
 * 3.bean property
 * 4.bean scope
 * <p>
 * implementations below ↓
 *
 * @author ShaoJiale
 * date 2019/12/10
 * @see org.litespring.beans.factory.support.GenericBeanDefinition
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    String getID();

    boolean hasConstructorArgumentValues();

    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    Class<?> getBeanClass() throws IllegalStateException;

    boolean hasBeanClass();
}
