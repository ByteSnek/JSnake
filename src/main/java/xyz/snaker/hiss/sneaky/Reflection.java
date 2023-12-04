package xyz.snaker.hiss.sneaky;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 26/09/2023
 **/
public class Reflection
{
    public static <T> T getFieldDirect(Class<?> clazz, String name, boolean isPrivate, @Nullable Object obj)
    {
        try {
            Field field = clazz.getDeclaredField(name);
            if (isPrivate) {
                field.setAccessible(true);
            }
            return Sneaky.cast(field.get(obj));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T[] getFieldsInClass(Class<?> clazz, Predicate<? super Object> filter, IntFunction<T[]> generator)
    {
        return Sneaky.cast(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> getFieldDirect(clazz, field.getName(), Modifier.isPrivate(field.getModifiers()), null))
                .filter(filter)
                .toArray(generator)
        );
    }

    public static <T> T getRandomFieldInClass(Class<?> clazz, @Nullable Predicate<? super Object> filter, IntFunction<T[]> generator)
    {
        return getRandom(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> getFieldDirect(clazz, field.getName(), Modifier.isPrivate(field.getModifiers()), null))
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

    static <T> T getRandom(T[] values, Random random)
    {
        return values[random.nextInt(values.length)];
    }
}
