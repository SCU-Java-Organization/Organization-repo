package org.litespring.beans;

/**
 * Description: Class of property tag
 * A property can be a bean or a value
 *
 * @author ShaoJiale
 * date 2019/12/12
 */
public class PropertyValue {
    private final String name;

    private final Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }

    public synchronized void setConvertedValue(Object value) {
        this.converted = true;
        this.convertedValue = value;
    }
}
