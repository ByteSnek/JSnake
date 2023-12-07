package xyz.snaker.hiss.sneaky;

import java.lang.reflect.Method;

/**
 * Created by SnakerBone on 8/12/2023
 **/
public class References
{
    static final Error ERR_GLFW_NOT_FOUND = new Error("Could not find class org.lwjgl.glfw.GLFW in current project");
    static final Error ERR_LANG3_TIME_NOT_FOUND = new Error("Could not find class org.apache.commons.lang3.time.DurationFormatUtils in current project");

    public static Class<?> getGlfw()
    {
        try {
            return Reflection.getUncheckedClass("org.lwjgl.glfw.GLFW");
        } catch (Exception e) {
            throw ERR_GLFW_NOT_FOUND;
        }
    }

    public static Class<?> getDurationFormatUtils()
    {
        try {
            return Reflection.getUncheckedClass("org.apache.commons.lang3.time.DurationFormatUtils");
        } catch (Exception e) {
            throw ERR_LANG3_TIME_NOT_FOUND;
        }
    }

    public static int glfwGetKey(long handle, int key)
    {
        Class<?> clazz = getGlfw();
        Method method = Reflection.getUncheckedMethod(clazz, "glfwGetKey", long.class, int.class);

        return Reflection.invokeUncheckedMethod(method, null, handle, key);
    }

    public static String formatDuration(long time, String format)
    {
        Class<?> clazz = getDurationFormatUtils();
        Method method = Reflection.getUncheckedMethod(clazz, "formatDuration", long.class, String.class);

        return Reflection.invokeUncheckedMethod(method, null, time, format);
    }
}
