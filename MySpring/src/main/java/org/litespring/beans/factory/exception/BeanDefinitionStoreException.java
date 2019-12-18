package org.litespring.beans.factory.exception;

import org.litespring.beans.BeansException;

/**
 * Description: Exception of reading XML file
 *
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
