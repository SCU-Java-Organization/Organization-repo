package org.litespring.beans.factory.support;

import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.exception.*;
import org.litespring.beans.propertyEditors.CustomBooleanEditor;
import org.litespring.beans.propertyEditors.CustomNumberEditor;
import org.litespring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Convert Strings into Numbers or Booleans
 * This is different from the BeanDefinition resolver, which
 * is used for resolving RuntimeBeans or Strings.
 *
 * @author ShaoJiale
 * date 2019/12/13
 * @see BeanDefinitionValueResolver
 */
public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {
    }

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requiredType, value)) {         // the same type
            return (T) value;
        } else {
            if (value instanceof String) {                                // value is a String
                PropertyEditor editor = findDefaultEditor(requiredType);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requiredType);
                }
                return (T) editor.getValue();
            }
            throw new RuntimeException("Todo: cannot convert value for " + value + " class" + requiredType);
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        PropertyEditor editor = getDefaultEditor(requiredType);
        if (editor == null)
            throw new RuntimeException("Editor for " + requiredType + " has not been implemented");
        return editor;
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if (this.defaultEditors == null)
            createDefaultEditors();
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);

        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));

        //TODO: more types
    }
}
