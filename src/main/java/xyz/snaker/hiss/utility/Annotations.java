package xyz.snaker.hiss.utility;

import java.lang.annotation.Annotation;

/**
 * Created by SnakerBone on 22/08/2023
 **/
public class Annotations
{
    public static <V> boolean isPresent(V obj, Class<? extends Annotation> annotationClass)
    {
        return obj.getClass().isAnnotationPresent(annotationClass);
    }

    public static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotationClass)
    {
        return clazz.isAnnotationPresent(annotationClass);
    }
}
