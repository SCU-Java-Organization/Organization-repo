package org.litespring.beans;

import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.beans.factory.exception.BeanDefinitionStoreException;

/**
 * Description: Base exception class
 *
 * @see BeanCreationException
 * @see BeanDefinitionStoreException
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause){
        super(msg, cause);
    }
}
