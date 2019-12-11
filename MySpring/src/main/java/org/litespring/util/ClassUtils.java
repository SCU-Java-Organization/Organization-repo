package org.litespring.util;

/**
 * description: Utils of getting a ClassLoader
 * @author ShaoJiale
 * date 2019/12/10
 */
public abstract class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader loader = null;

        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex){
            // Cannot access thread context ClassLoader
        }

        if(loader == null){
            // No thread context ClassLoader, use ClassLoader of this class
            loader = ClassUtils.class.getClassLoader();

            if(loader == null){
                // Returning null means we're using bootstrap ClassLoader
                try {
                    loader = ClassLoader.getSystemClassLoader();
                } catch (Throwable e){
                    // Cannot access system ClassLoader
                }
            }
        }
        return loader;
    }

}