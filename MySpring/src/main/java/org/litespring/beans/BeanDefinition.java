package org.litespring.beans;

/**
 * Description: definition of bean
 *
 * @see org.litespring.beans.factory.support.GenericBeanDefinition
 * @author ShaoJiale
 * date 2019/12/10
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
}
