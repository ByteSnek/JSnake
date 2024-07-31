package xyz.snaker.jsnake.system;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public interface DynamicLoader<T>
{
    void load(Class<? extends T> clazz, boolean logSuccess);
}
