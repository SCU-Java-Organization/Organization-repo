package org.litespring.test.version2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyEditors.CustomNumberEditor;

/**
 * Description: Test CustomNumberEditor
 * Test if we can parse a String into a Number
 * @author ShaoJiale
 * date 2019/12/12
 */
public class CustomNumberEditorTest {
    @Test
    public void testConvertString(){
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer)editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
