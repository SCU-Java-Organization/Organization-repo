package org.litespring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: Utils of Class
 *
 * @author ShaoJiale
 * date 2019/12/10
 */
public abstract class ClassUtils {

    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<>();
    private static final Map<Class<?>, Class<?>> wrapperToPrimitiveTypeMap = new HashMap<>();

    static {
        wrapperToPrimitiveTypeMap.put(Boolean.class, boolean.class);
        wrapperToPrimitiveTypeMap.put(Byte.class, byte.class);
        wrapperToPrimitiveTypeMap.put(Character.class, char.class);
        wrapperToPrimitiveTypeMap.put(Double.class, double.class);
        wrapperToPrimitiveTypeMap.put(Float.class, float.class);
        wrapperToPrimitiveTypeMap.put(Integer.class, int.class);
        wrapperToPrimitiveTypeMap.put(Long.class, long.class);
        wrapperToPrimitiveTypeMap.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : wrapperToPrimitiveTypeMap.entrySet())
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
    }

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

    public static boolean isAssignableValue(Class<?> type, Object value) {
        Assert.notNull(type, "Type must not be null");
        return value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive();
    }

    public static boolean isAssignable(Class<?> leftType, Class<?> rightType) {
        Assert.notNull(leftType, "Left side type must not be null");
        Assert.notNull(rightType, "right side type must not be null");

        if (leftType.isAssignableFrom(rightType)) {
            return true;
        }

        if (leftType.isPrimitive()){
            Class<?> resolvedPrimitive = wrapperToPrimitiveTypeMap.get(rightType);
            if (resolvedPrimitive != null && leftType.equals(resolvedPrimitive)){
                return true;
            }
        } else {
            Class<?> resolvedWrapper = primitiveTypeToWrapperMap.get(rightType);
            if (resolvedWrapper != null && leftType.isAssignableFrom(resolvedWrapper)){
                return true;
            }
        }
        return false;
    }
}
