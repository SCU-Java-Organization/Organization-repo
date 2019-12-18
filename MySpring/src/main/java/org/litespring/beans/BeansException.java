package org.litespring.beans;

/**
 * Description: Base exception class
 * Subclasses are in the package below.
 *
 * @author ShaoJiale
 * date 2019/12/10
 * @see org.litespring.beans.factory.exception
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
