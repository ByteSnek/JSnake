package xyz.snaker.hiss.keyboard;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import xyz.snaker.hiss.sneaky.References;
import xyz.snaker.hiss.sneaky.Reflection;
import xyz.snaker.hiss.utility.Pair;

/**
 * Created by SnakerBone on 17/10/2023
 **/
public class KeyPair
{
    /**
     * Super Keys
     **/
    public static final Function<Long, KeyPair> SUPER = handle -> new KeyPair(handle, 343, 347);
    /**
     * Shift Keys
     **/
    public static final Function<Long, KeyPair> SHIFT = handle -> new KeyPair(handle, 340, 344);
    /**
     * Control Keys
     **/
    public static final Function<Long, KeyPair> CONTROL = handle -> new KeyPair(handle, 341, 345);
    /**
     * Alternate Keys
     **/
    public static final Function<Long, KeyPair> ALTERNATE = handle -> new KeyPair(handle, 342, 346);

    /**
     * Internal pair holding the keys
     **/
    private final Pair<Integer, Integer> keyPair;

    /**
     * The handle of the current window
     **/
    private final long handle;

    public KeyPair(long handle, int left, int right)
    {
        validateKeys(left, right);

        this.keyPair = new Pair<>(left, right);
        this.handle = handle;
    }

    /**
     * Checks if a key is invalid
     *
     * @param key The key to check
     * @return True if the key is invalid or does not exist
     **/
    private static boolean isInvalidKey(int key)
    {
        Integer[] rawValues = Reflection.getFieldsInClass(References.getGlfw(), o -> o instanceof Integer, Integer[]::new);
        List<Integer> values = Arrays.stream(rawValues).toList();

        return !values.contains(key);
    }

    /**
     * Checks if a key is invalid
     *
     * @param keys The keys to check
     * @throws IllegalArgumentException If a key is invalid
     **/
    private static void validateKeys(int... keys)
    {
        for (int key : keys) {
            if (isInvalidKey(key)) {
                throw new IllegalArgumentException("Key '%s' not found. Key should be a value of one of the following fields present in org.lwjgl.glfw.GLFW or similar".formatted(key));
            }
        }
    }

    /**
     * Gets the 'left' variant of this KeyPair
     *
     * @return The 'left' key
     **/
    public int left()
    {
        return keyPair.getLeft();
    }

    /**
     * Gets the 'right' variant of this KeyPair
     *
     * @return The 'right' key
     **/
    public int right()
    {
        return keyPair.getRight();
    }

    /**
     * Base method for checking if a key is being pressed
     *
     * @param key The key to check
     * @return True if the key is currently being pressed
     **/
    private boolean isDown(int key)
    {
        return Keyboard.isKeyDown(handle, key);
    }

    /**
     * Base method for checking if a key is not being pressed
     *
     * @param key The key to check
     * @return True if the key is not currently being pressed
     **/
    private boolean isUp(int key)
    {
        return Keyboard.isKeyUp(handle, key);
    }

    /**
     * Checks if the 'left' key is being pressed
     *
     * @return True if the key is currently being pressed
     **/
    public boolean leftDown()
    {
        return isDown(left());
    }

    /**
     * Checks if the 'right' key is being pressed
     *
     * @return True if the key is currently being pressed
     **/
    public boolean rightDown()
    {
        return isDown(right());
    }

    /**
     * Checks if the 'left' key is not being pressed
     *
     * @return True if the key is currently not being pressed
     **/
    public boolean leftUp()
    {
        return isUp(left());
    }

    /**
     * Checks if the 'right' key is not being pressed
     *
     * @return True if the key is currently not being pressed
     **/
    public boolean rightUp()
    {
        return isUp(right());
    }

    /**
     * Checks if both keys in this KeyPair are being pressed
     *
     * @return True if both keys in this KeyPair are currently being pressed
     **/
    public boolean allDown()
    {
        return leftDown() & rightDown();
    }

    /**
     * Checks if both keys in this KeyPair are not being pressed
     *
     * @return True if both keys in this KeyPair are currently not being pressed
     **/
    public boolean allUp()
    {
        return leftUp() & rightUp();
    }

    /**
     * Checks if any keys in this KeyPair are being pressed
     *
     * @return True if any keys in this KeyPair are currently being pressed
     **/
    public boolean anyDown()
    {
        return leftDown() | rightDown();
    }

    /**
     * Checks if any keys in this KeyPair are not being pressed
     *
     * @return True if any keys in this KeyPair are not currently being pressed
     **/
    public boolean anyUp()
    {
        return leftUp() | rightUp();
    }

    /**
     * Checks if any singular key in this KeyPair is being pressed
     *
     * @return True if a single key in this KeyPair is currently being pressed
     **/
    public boolean sequentialDown()
    {
        return leftDown() ^ rightDown();
    }

    /**
     * Checks if any singular key in this KeyPair is not being pressed
     *
     * @return True if a single key in this KeyPair is not currently being pressed
     **/
    public boolean sequentialUp()
    {
        return leftUp() ^ rightUp();
    }
}
