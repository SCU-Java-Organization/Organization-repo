package org.litespring.beans.factory.config;

/**
 * Description: A class for the property tag
 * We use this to describe the String value in the property tag,
 * such as <property name="enq" value="hello world"/>.
 * Another condition is about bean reference:
 *
 * @see RuntimeBeanReference
 * @see org.litespring.beans.PropertyValue
 * @author ShaoJiale
 * date 2019/12/12
 */
public class TypedStringValue {
    private String value;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
