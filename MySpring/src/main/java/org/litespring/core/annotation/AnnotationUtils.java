package org.litespring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

/**
 * Description: Utils for annotation
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class AnnotationUtils {
    /**
     * Get annotation from a field
     *
     * @param obj  field
     * @param type Class of an annotation, such as Autowired.class
     * @param <T>  a kind of annotation
     * @return a kind of annotation
     */
    public static <T extends Annotation> T getAnnotation(AccessibleObject obj, Class<? extends Annotation> type) {
        T annotation = (T) obj.getAnnotation(type);
        if (annotation == null) {
            for (Annotation metaAnn : obj.getAnnotations()) {
                if (annotation != null)
                    break;
            }
        }
        return annotation;
    }
}
