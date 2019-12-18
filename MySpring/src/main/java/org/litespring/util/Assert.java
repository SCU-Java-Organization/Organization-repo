package org.litespring.util;

/**
 * Description: Determine if an object is null
 *
 * @author ShaoJiale
 * date 2019/12/11
 */
public abstract class Assert {
    public static void notNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }
}
