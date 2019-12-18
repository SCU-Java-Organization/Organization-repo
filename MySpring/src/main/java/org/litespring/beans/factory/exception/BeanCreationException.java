package org.litespring.beans.factory.exception;

import org.litespring.beans.BeansException;

/**
 * Description: Exception of creating a bean
 *
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeanCreationException extends BeansException {
    private String beanName;

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, String beanName) {
        super("Error creating bean with name = " + beanName + "" + msg);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(msg, beanName);
        initCause(cause);
    }

    public String getBeanName() {
        return this.beanName;
    }
}
