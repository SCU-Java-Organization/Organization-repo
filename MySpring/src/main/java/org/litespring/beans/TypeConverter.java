package org.litespring.beans;

import org.litespring.beans.factory.exception.*;

/**
 * Description: Convert value into required type by implementing this interface
 * This interface is different from BeanDefinitionResolver, this is used for converting
 * String to Numbers, while resolver is used for resolving RuntimeBean or String values.
 *
 * @author ShaoJiale
 * date 2019/12/13
 * @see org.litespring.beans.factory.support.BeanDefinitionValueResolver
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
