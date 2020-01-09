package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionRegistry;

/**
 * Description: A bean name generator interface.
 *
 * @author ShaoJiale
 * date 2020/1/9
 */
public interface BeanNameGenerator {
    /**
     * Generate a bean name for the given bean definition.
     *
     * @param definition the bean definition to generate a name for
     * @param registry   the bean definition registry that the given
     *                   definition is supposed to be registered with
     * @return the generated bean name
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
