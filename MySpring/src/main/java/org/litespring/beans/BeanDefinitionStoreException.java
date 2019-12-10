package org.litespring.beans;

/**
 * description: Exception of reading XML file
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg, Throwable cause){
        super(msg, cause);
    }
}
