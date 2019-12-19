package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Description: A class for constructor arguments
 * This class was created to support constructor DI in the XML file.
 *
 * @author ShaoJiale
 * date 2019/12/18
 */
public class ConstructorArgument {
    private final List<ValueHolder> argumentValues = new LinkedList<>();

    public ConstructorArgument() {
    }

    public void addArgumentValue(ValueHolder holder) {
        this.argumentValues.add(holder);
    }

    public List<ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.argumentValues);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    public void clear() {
        this.argumentValues.clear();
    }

    /**
     * This inner class was created to keep value in the constructor config.
     * We haven't support 'type' or 'name' yet, but we may implement them in
     * the future.
     * <p>
     * Supported:
     * <constructor-arg ref="runtimeBean"/>
     * <constructor-arg value="StringOrNumberOrBoolean"/>
     *
     * <p>
     * Not supported yet:
     * <constructor-arg name="xxx" ref="xxx"/>
     * <constructor-arg type="String" value="xxx"/>
     * <constructor-arg index="1 ref="xxx"/>
     */
    public static class ValueHolder {
        private Object value;

        private String type;

        private String name;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this.value = value;
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this.value = value;
            this.type = type;
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
