package org.litespring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
