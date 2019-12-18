package org.litespring.beans.propertyEditors;

import org.litespring.util.NumberUtils;
import org.litespring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * Description: An custom number editor for resolving String
 * Use this editor to resolve the Number value in 'property' tag,
 * in order to inject Number dependencies for beans.
 * <p>
 * We save wrapper data by extending PropertyEditorSupport and
 * override its methods...
 *
 * @author ShaoJiale
 * date 2019/12/13
 * @see NumberUtils
 * @see CustomBooleanEditor
 */
public class CustomNumberEditor extends PropertyEditorSupport {
    private final Class<? extends Number> numberClass;

    private final NumberFormat numberFormat;

    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty)
            throws IllegalArgumentException {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty)
            throws IllegalArgumentException {
        if (numberClass == null || !Number.class.isAssignableFrom(numberClass))
            throw new IllegalArgumentException("Property class must be a subclass of Number");

        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text))
            setValue(null);
        else if (this.numberFormat != null)
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        else
            setValue(NumberUtils.parseNumber(text, this.numberClass));
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number)
            super.setValue(NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
        else
            super.setValue(value);
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        if (value == null)
            return "";
        if (this.numberFormat != null)
            return this.numberFormat.format(value);
        return value.toString();
    }
}
