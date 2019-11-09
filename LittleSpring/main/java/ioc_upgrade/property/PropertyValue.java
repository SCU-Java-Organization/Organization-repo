package ioc_upgrade.property;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function bean中的单个property实体类
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
