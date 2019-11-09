package ioc_upgrade.property;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function bean中的property列表实体类
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue value){
        /**
         * 将List封装的唯一作用在于这里
         * 在add方法调用之前可以对PropertyValue做一些处理
         * 若不封装直接使用List<PropertyValue>则无法处理
         * 此时我不对value做任何处理
         * 这么写只是为了设计上的一种规范
         */
        this.propertyValueList.add(value);
    }

    public List<PropertyValue> getPropertyValues(){
        return this.propertyValueList;
    }
}
