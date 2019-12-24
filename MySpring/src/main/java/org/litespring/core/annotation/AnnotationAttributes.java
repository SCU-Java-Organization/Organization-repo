package org.litespring.core.annotation;

import org.litespring.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: a LinkedHashMap which contains attribute name and attribute instance
 * This class is a container which is used by AnnotationAttributesReadingVisitor when
 * asm is resolving attributes of a annotation.
 *
 * @author ShaoJiale
 * date 2019/12/23
 */

public class AnnotationAttributes extends LinkedHashMap<String, Object> {
    public AnnotationAttributes() {

    }

    public AnnotationAttributes(int initialCapacity) {
        super(initialCapacity);
    }

    public AnnotationAttributes(Map<String, Object> map) {
        super(map);
    }

    public String getString(String attributeName) {
        return doGet(attributeName, String.class);
    }

    public String[] getStringArray(String attributeName) {
        return doGet(attributeName, String[].class);
    }

    public boolean getBoolean(String attributeName) {
        return doGet(attributeName, Boolean.class);
    }

    @SuppressWarnings("unchecked")
    public <N extends Number> N getNumber(String attributeName) {
        return (N) doGet(attributeName, Integer.class);
    }

    @SuppressWarnings("unchecked")
    public <E extends Enum<?>> E getEnum(String attributeName) {
        return (E) doGet(attributeName, Enum.class);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getClass(String attributeName) {
        return doGet(attributeName, Class.class);
    }

    public Class<?>[] getClassArray(String attributeName) {
        return doGet(attributeName, Class[].class);
    }


    @SuppressWarnings("unchecked")
    private <T> T doGet(String attributeName, Class<T> expectedType) {
        Object value = this.get(attributeName);
        Assert.notNull(value, String.format("Attribute '%s' not found", attributeName));
        return (T) value;
    }
}
