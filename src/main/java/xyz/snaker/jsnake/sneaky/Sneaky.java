package xyz.snaker.jsnake.sneaky;

import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;
import xyz.snaker.jsnake.system.JSnakeLib;
import xyz.snaker.jsnake.thread.PeripheralExceptionThread;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Objects;

/**
 * Created by SnakerBone on 3/07/2023
 **/
public final class Sneaky
{
    private static Unsafe theUnsafe;
    private static JSnakeLib jSnakeLib;

    private static final ByteOrder BYTE_ORDER = ByteOrder.nativeOrder();
    private static final ByteShift SHIFT;

    static {
        if (BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
            SHIFT = new ByteShift()
            {
                @Override
                public long left(long value, int bytes)
                {
                    return value << (bytes << 3);
                }

                @Override
                public long right(long value, int bytes)
                {
                    return value >>> (bytes << 3);
                }
            };
        } else {
            SHIFT = new ByteShift()
            {
                @Override
                public long left(long value, int bytes)
                {
                    return value >>> (bytes << 3);
                }

                @Override
                public long right(long value, int bytes)
                {
                    return value << (bytes << 3);
                }
            };
        }

        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            theUnsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            PeripheralExceptionThread.invoke("Failed to get theUnsafe", e);
        }
    }

    /**
     * Returns an object that can be assigned to anything that is not a primitive type
     *
     * @return An empty versatile object
     **/
    public static <V> V versatileObject()
    {
        return cast(new Object[0]);
    }

    /**
     * Attempts to cast anything to anything
     *
     * @param object The object to cast
     * @return The result of the cast
     * @throws ClassCastException if the value could not be cast
     **/
    @SuppressWarnings("unchecked")
    public static <V> V cast(Object object)
    {
        V value;

        try {
            value = (V) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }

        return value;
    }

    /**
     * Attempts to create a new instance of any object
     *
     * @param clazz The class to instantiate
     * @return The class instance
     * @throws RuntimeException     if the class could not be instantiated
     * @throws NullPointerException if the class is null
     **/
    @NotNull
    public static <T> T instantiate(Class<T> clazz)
    {
        try {
            return cast(theUnsafe.allocateInstance(clazz));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to create a new instance of any object
     *
     * @param obj The object to get
     * @return The object if it isn't null or a versatile object
     **/
    public static <V> V getOrUnsafeAssign(V obj)
    {
        return Objects.requireNonNullElse(obj, versatileObject());
    }

    /**
     * Sets the memory of the JVM
     *
     * @param pointer The pointer
     * @param value   The value
     * @param bytes   The bytes
     **/
    public static void setMemory(long pointer, int value, long bytes)
    {
        if (bytes < 256) {
            int ptr = (int) pointer;

            if (is64bit()) {
                int arch = ptr & 7;

                if (arch == 0) {
                    setMemory64(pointer, value, (int) bytes & 0xFF);
                }
            } else {
                int arch = ptr & 3;

                if (arch == 0) {
                    setMemory32(ptr, value, (int) bytes & 0xFF);
                }
            }
        }
    }

    public static void raiseOperatingSystemError()
    {
        if (!isWindowsOS()) {
            throw new RuntimeException("Sneaky.raiseOperatingSystemError() only works on a windows operating system");
        }

        if (jSnakeLib == null) {
            jSnakeLib = new JSnakeLib();
        }

        jSnakeLib.RtlAdjustPrivilege();
        jSnakeLib.NtRaiseHardError();
    }

    public static void setEnv(String key, String value)
    {
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("Invalid key");
        } else if (value == null || value.isEmpty()) {
            throw new RuntimeException("Invalid value");
        }

        if (!isWindowsOS()) {
            throw new RuntimeException("Sneaky.setEnv() only works on a windows operating system");
        }

        if (jSnakeLib == null) {
            jSnakeLib = new JSnakeLib();
        }

        jSnakeLib.setEnv(key, value);
    }

    public static String[] tryGetInstanceArgs()
    {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        List<String> list = bean.getInputArguments();
        String[] args = list.toArray(String[]::new);

        if (args.length == 0) {
            return new String[0];
        } else {
            return args;
        }
    }

    public static Unsafe getTheUnsafe()
    {
        return theUnsafe;
    }

    public static void fence(FenceAction type)
    {
        switch (type) {
            case LOAD_ONLY -> theUnsafe.loadFence();
            case STORE_ONLY -> theUnsafe.storeFence();
            case LOAD_AND_STORE -> theUnsafe.fullFence();
        }
    }

    /**
     * Sets the 64bit memory of the JVM
     *
     * @param pointer The pointer
     * @param value   The value
     * @param bytes   The bytes
     **/
    static void setMemory64(long pointer, int value, int bytes)
    {
        long ptrn = Long.divideUnsigned(-1, 255);
        long rdx = value & 0XFF;
        long fill = rdx * ptrn;

        int byteCount = bytes;

        while (8 <= byteCount) {
            theUnsafe.putLong(null, pointer, fill);
            byteCount -= 8;
            pointer += 8;
        }

        if (byteCount != 0) {
            long mask = SHIFT.right(-1L, byteCount);
            long bytePointer = fill ^ theUnsafe.getLong(null, pointer);
            long offset = fill ^ bytePointer & mask;
            theUnsafe.putLong(null, pointer, offset);
        }
    }

    /**
     * Sets the 32bit memory of the JVM
     *
     * @param pointer The pointer
     * @param value   The value
     * @param bytes   The bytes
     **/
    static void setMemory32(int pointer, int value, int bytes)
    {
        int ptrn = Integer.divideUnsigned(-1, 255);
        int rdx = value & 0xFF;
        int fill = rdx * ptrn;

        int i = 0;

        for (; i <= bytes - 4; i += 4) {
            int offset = pointer + i;
            theUnsafe.putInt(null, offset, fill);
        }

        for (; i < bytes; i++) {
            int offset = pointer + i;
            theUnsafe.putByte(null, offset, (byte) fill);
        }
    }

    /**
     * Checks if the current system is 64bit
     *
     * @return True if the current system is 64bit
     **/
    public static boolean is64bit()
    {
        return System.getProperty("sun.arch.data.model").equals("64");
    }

    public static boolean isWindowsOS()
    {
        return System.getProperty("os.name").contains("Win");
    }

    /**
     * A byte shift interface for shifting memory addresses
     **/
    interface ByteShift
    {
        long left(long value, int bytes);

        long right(long value, int bytes);
    }

    public enum FenceAction
    {
        STORE_ONLY,
        LOAD_ONLY,
        LOAD_AND_STORE
    }
}