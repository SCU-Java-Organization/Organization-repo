package org.litespring.beans.factory.config;

/**
 * Description: A class for the property tag
 * We use this to describe the String value in the property tag,
 * such as <property name="enq" value="hello world"/>.
 * Another condition is about bean reference:
 *
 * @author ShaoJiale
 * date 2019/12/12
 * @see RuntimeBeanReference
 * @see org.litespring.beans.PropertyValue
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
