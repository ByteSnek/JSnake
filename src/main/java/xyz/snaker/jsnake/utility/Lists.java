package xyz.snaker.jsnake.utility;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SnakerBone on 27/04/24
 * <p>
 * Licensed under MIT
 **/
public class Lists
{
    @SafeVarargs
    public static <T> List<T> join(List<T>... lists)
    {
        List<T> list = new LinkedList<>();

        for (List<T> entry : lists) {
            list.addAll(entry);
        }

        return list;
    }

    public static <T> String format(List<T> list)
    {
        List<String> strings = list
                .stream()
                .map(T::toString)
                .toList();
        StringBuilder builder = new StringBuilder();

        for (String string : strings) {
            builder.append(string);
            builder.append(", ");
            builder.append("\n\n");
        }

        return builder.toString();
    }
}
