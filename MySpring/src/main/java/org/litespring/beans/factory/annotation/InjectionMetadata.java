package org.litespring.beans.factory.annotation;

import java.util.List;

/**
 * Description: Use this class to inject dependencies into a target class
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class InjectionMetadata {
    private final Class<?> targetClass;

    List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return this.injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty())
            return;
        for (InjectionElement elem : injectionElements)
            elem.inject(target);
    }
}
