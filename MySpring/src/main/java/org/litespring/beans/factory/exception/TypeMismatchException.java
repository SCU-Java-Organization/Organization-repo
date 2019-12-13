package org.litespring.beans.factory.exception;

import org.litespring.beans.BeansException;

/**
 * Description: Exception about class cast
 *
 * @author ShaoJiale
 * date 2019/12/13
 */
public class TypeMismatchException extends BeansException {
    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value: " + value + " to type " + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
