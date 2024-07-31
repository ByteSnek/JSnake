package xyz.snaker.jsnake.system;

import java.util.Map;

/**
 * Created by SnakerBone on 1/12/2023
 **/
public interface DynamicLink<T>
{
    Map<String, Class<? extends T>> libs();
}
