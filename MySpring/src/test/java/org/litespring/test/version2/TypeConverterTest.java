package org.litespring.test.version2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.exception.*;
import org.litespring.beans.factory.support.SimpleTypeConverter;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/13
 */
public class TypeConverterTest {
    @Test
    public void testConvertString2Int(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e){
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertString2Boolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try {
            converter.convertIfNecessary("invalid input", Boolean.class);
        } catch (TypeMismatchException e){
            return;
        }
        Assert.fail();
    }
}
