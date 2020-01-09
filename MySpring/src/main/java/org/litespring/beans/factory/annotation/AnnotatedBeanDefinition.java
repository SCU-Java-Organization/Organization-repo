package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * Description: Annotated bean definition interface
 *
 * @author ShaoJiale
 * date 2020/1/9
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
