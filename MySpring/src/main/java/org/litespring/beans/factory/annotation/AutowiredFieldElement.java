package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Description: Use this class to autowire fields into target class.
 *
 * @author ShaoJiale
 * date 2020/1/10
 * @see org.litespring.beans.factory.annotation.InjectionElement
 */
public class AutowiredFieldElement extends InjectionElement {
    boolean required;

    public AutowiredFieldElement(Field field, boolean required, AutowireCapableBeanFactory factory) {
        super(field, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field)this.member;
    }

    /**
     * Inject fields into target bean instance.
     * @param target target bean
     */
    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(descriptor);

            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex.getMessage());
        }
    }
}
