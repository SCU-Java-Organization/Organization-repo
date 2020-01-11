package org.litespring.beans.factory.config;

import org.litespring.util.Assert;

import java.lang.reflect.Field;

/**
 * Description: A descriptor for dependency.
 * This descriptor contains fields and if each
 * of them is required by the Component.
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class DependencyDescriptor {

    private Field field;

    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if (this.field != null)
            return field.getType();
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
