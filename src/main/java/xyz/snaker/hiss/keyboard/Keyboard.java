package xyz.snaker.hiss.keyboard;

import org.lwjgl.glfw.GLFW;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Keyboard
{
    /**
     * The current window handle
     **/
    private static long handle;

    static {
        initialize();
    }

    /**
     * Checks if a key is being pressed
     *
     * @param key A {@link GLFW} printable key
     * @return True if the key is currently being pressed
     **/
    public static boolean isKeyDown(int key)
    {
        return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_PRESS;
    }

    /**
     * Checks if a key is being pressed
     *
     * @param handle The window of the current {@link GLFW} window
     * @param key    A {@link GLFW} printable key
     * @return True if the key is currently being pressed
     **/
    public static boolean isKeyDown(long handle, int key)
    {
        return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_PRESS;
    }

    /**
     * Checks if a key is not being pressed
     *
     * @param key A {@link GLFW} printable key
     * @return True if the key is currently not being pressed
     **/
    public static boolean isKeyUp(int key)
    {
        return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_RELEASE;
    }

    /**
     * Checks if a key is not being pressed
     *
     * @param handle The window of the current {@link GLFW} window
     * @param key    A {@link GLFW} printable key
     * @return True if the key is currently not being pressed
     **/
    public static boolean isKeyUp(long handle, int key)
    {
        return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_RELEASE;
    }

    /**
     * Attempts to set the current window handle
     **/
    static void initialize()
    {
        handle = GLFW.glfwGetCurrentContext();
    }
}
