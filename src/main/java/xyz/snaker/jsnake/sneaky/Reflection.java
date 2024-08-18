package xyz.snaker.jsnake.sneaky;

import javax.annotation.Nullable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class Reflection
{
    public static <T> T getFieldDirect(Class<?> clazz, String name, @Nullable Object instance)
    {
        try {
            Field field = clazz.getDeclaredField(name);
            setAccessibleIfNeeded(field);

            return Sneaky.cast(field.get(instance));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T[] getFieldsInClass(Class<?> clazz, Predicate<? super Object> filter, IntFunction<T[]> generator, @Nullable Object instance)
    {
        return Sneaky.cast(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> getFieldDirect(clazz, field.getName(), instance))
                .filter(filter)
                .toArray(generator)
        );
    }

    public static <T> T getRandomFieldInClass(Class<?> clazz, @Nullable Predicate<? super Object> filter, IntFunction<T[]> generator, @Nullable Object instance)
    {
        return getRandom(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> getFieldDirect(clazz, field.getName(), instance))
                .filter(filter == null ? o -> true : filter)
                .toArray(generator), new Random()
        );
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj       The field to find
     * @param parent    The class where the field is present
     * @param lowercase Should the result be lowercase
     * @return The field name or null if the class has no fields or accessible fields
     **/
    public static String getFieldName(Object obj, Class<?> parent, boolean lowercase)
    {
        Field[] fields = parent.getFields();

        for (Field field : fields) {
            Object object;

            try {
                object = field.get(parent);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (obj.equals(object)) {
                return lowercase ? field.getName().toLowerCase() : field.getName();
            }
        }

        return null;
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj    The field to find
     * @param parent The class where the field is present
     * @return The field name or null if the class has no fields or accessible fields
     **/
    public static String getFieldName(Object obj, Class<?> parent)
    {
        return getFieldName(obj, parent, true);
    }

    public static Class<?> getUncheckedClass(String className)
    {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getUncheckedMethod(Class<?> clazz, String name, Class<?>... params)
    {
        try {
            Method method = clazz.getDeclaredMethod(name, params);
            setAccessibleIfNeeded(method);
            return clazz.getDeclaredMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T invokeUncheckedMethod(Method method, @Nullable Object instance, Object... args)
    {
        try {
            setAccessibleIfNeeded(method);
            return Sneaky.cast(method.invoke(instance, args));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T getRandom(T[] values, Random random)
    {
        return values[random.nextInt(values.length)];
    }

    static <T extends AccessibleObject & Member> void setAccessibleIfNeeded(T obj)
    {
        obj.setAccessible(isUnaccessible(obj));
    }

    static <T extends AccessibleObject & Member> boolean isUnaccessible(T obj)
    {
        int modifiers = obj.getModifiers();

        return Modifier.isPrivate(modifiers) || Modifier.isProtected(modifiers);
    }
}
