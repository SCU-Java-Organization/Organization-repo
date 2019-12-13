package org.litespring.test.version2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyEditors.CustomBooleanEditor;

/**
 * Description: Test CustomBooleanEditor
 *
 * @author ShaoJiale
 * date 2019/12/13
 */
public class CustomBooleanTest {
    @Test
    public void testConvertStringToBoolean(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        try {
            editor.setAsText("112233");
        } catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }

}
