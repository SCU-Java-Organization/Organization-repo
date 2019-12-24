package org.litespring.stereotype;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
