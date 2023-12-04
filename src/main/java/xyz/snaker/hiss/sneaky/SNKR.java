package xyz.snaker.hiss.sneaky;

import xyz.snaker.hiss.system.DLL;

/**
 * <b>PLEASE DO NOT TRY TO ACCESS THIS CLASS VIA ASM/REFLECTION</b>
 * <p>
 * Illegal calling of these methods <b>WILL</b> invoke undefined behaviour
 * <p>
 * Please use {@link Sneaky} to invoke these methods without the risk of undefined behaviour
 * <p>
 * This library is currently only compatible with Windows 7 devices or newer
 * <p>
 * Created by SnakerBone on 13/11/2023
 **/
class SNKR implements DLL
{
    SNKR()
    {
        load(SNKR.class, true);
    }

    @Override
    public String getLibraryName()
    {
        return "snkr";
    }

    /**
     * Goodbye, World!
     * <p>
     * Call this at your own risk
     **/
    native void goodbyeWorld();

    /**
     * Gets uninitialized memory that is present on the current stack with the current index. Potentially can be unsafe due to UB and unwanted code execution before security checks are done. Can be useful as Java does not permit reading uninitialized memory addresses directly
     *
     * @param alloc The amount of memory to allocate to the uninitialized array (clamped between 0 and 1024)
     * @param i     The index of the memory array
     * @return The memory address at the specified index
     * @see Sneaky#getEarlyMemoryArray(int)
     **/
    native long getEarlyMemory(int alloc, int i);

    /**
     * De-references a null pointer. It would be rather silly to call this in production
     **/
    native void deRefNullPtr();

    /**
     * Creates a breakpoint for the current JVM
     *
     * @param v The flag to check for this breakpoint
     **/
    native void breakpointInstance(boolean v);

    /**
     * Permanently sets a user environment variable
     *
     * @param k The environment variable key
     * @param v The environment variable value
     **/
    native int setEnv(String k, String v);
}
