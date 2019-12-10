package org.litespring.beans;

/**
 * description: Base exception class
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
