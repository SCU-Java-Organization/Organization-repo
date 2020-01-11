package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Description: A processor which is used to build autowiring meta data
 * for a bean factory.
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class AutowireAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<>();

    public AutowireAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    /**
     * Build autowiring meta data for target class
     *
     * @param clazz target class
     * @return InjectionMetadata for target class
     * @see InjectionMetadata
     */
    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        // Injection elements of the target class and its super class
        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do {
            // Injection elements of the current class
            // it can be the target class or its super class
            LinkedList<InjectionElement> currElements = new LinkedList<>();

            // Find elements for the current class
            for (Field field : targetClass.getDeclaredFields()) {
                // Get annotation of the field
                // And test if the annotation is required
                Annotation annotation = findAutowiredAnnotation(field);
                if (annotation != null) {
                    if (Modifier.isStatic(field.getModifiers()))
                        continue;

                    boolean required = determineRequiredStatus(annotation);
                    currElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }

            for (Method method : targetClass.getDeclaredMethods()) {
                // TODO processing methods injection
            }

            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    /**
     * Determine if an annotation is required
     *
     * @param annotation a annotation
     * @return required status
     */
    protected boolean determineRequiredStatus(Annotation annotation) {
        try {
            // get the required() method
            // such as Autowired.required()
            Method method = ReflectionUtils.findMethod(annotation.annotationType(), this.requiredParameterName);

            // Annotations like @Inject and @Value don't have a method (attribute) named "required"
            // -> default to required status
            if (method == null) {
                return true;
            }

            // invoke required() method
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, annotation));
        } catch (Exception ex) {
            return true;
        }
    }

    /**
     * Find autowired annotation
     *
     * @param obj Field of the target class
     * @return annotation resolved from the field
     */
    private Annotation findAutowiredAnnotation(AccessibleObject obj) {
        // Test all the annotation types
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            // Find annotation of the field
            Annotation annotation = AnnotationUtils.getAnnotation(obj, type);
            if (annotation != null)
                return annotation;
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed");
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
