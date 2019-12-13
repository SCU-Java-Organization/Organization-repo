package org.litespring.beans;

import org.litespring.beans.factory.exception.*;

/**
 * Description: Convert value into required type by implementing this interface
 * This interface is different from BeanDefinitionResolver, this is used for converting
 * String to Numbers, while resolver is used for resolving RuntimeBean or String values.
 *
 * @see org.litespring.beans.factory.support.BeanDefinitionValueResolver
 * @author ShaoJiale
 * date 2019/12/13
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
