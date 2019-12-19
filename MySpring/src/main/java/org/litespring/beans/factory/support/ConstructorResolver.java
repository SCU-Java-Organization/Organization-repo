package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.exception.BeanCreationException;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Description: Find a proper constructor.
 * A XML config file can be very complex when you try to
 * configure a constructor-arg. For example, the count of the
 * args is not consistent, and for each of the args, its type
 * is not confirmed. So we need to find out the appropriate
 * constructor for the args configured in the XML file.
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
public class ConstructorResolver {
    protected final Log logger = LogFactory.getLog(getClass());

    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Find the appropriate constructor
     *
     * @param bd bean definition
     * @return a constructor
     */
    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = null;

        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException("Instantiation of bean failed, cannot resolve " + bd.getID(), bd.getID());
        }

        // candidate constructors
        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument constructorArgs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();

            // When the length of args doesn't match
            if (parameterTypes.length != constructorArgs.getArgumentCount())
                continue;

            argsToUse = new Object[parameterTypes.length];

            // Test if the current constructor matches the args
            boolean result = this.valueMatchTypes(parameterTypes,
                    constructorArgs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);

            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null)
            throw new BeanCreationException("cannot find a appropriate constructor", bd.getID());

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException("cannot find a create instance using " + constructorToUse.getName(), bd.getID());
        }
    }

    /**
     * Test if the args matches the current constructor
     *
     * @param parameterTypes parameter types of the current candidate constructor
     * @param valueHolders   value holder of the configured args
     * @param argsToUse      args array for the constructor
     * @param valueResolver  a resolver for TypedStringValue or RuntimeBeanReference
     * @param typeConverter  a converter for Number
     * @return true if matches
     */
    private boolean valueMatchTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> valueHolders,
                                    Object[] argsToUse,
                                    BeanDefinitionValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {

        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);

            // can be TypedStringValue or RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                // resolve into a bean or String value
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                // convert into Number if necessary
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);

                // no need to be worried about the args array, if this constructor doesn't match
                // the next candidate constructor will rewrite over it from index 0
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }
}
