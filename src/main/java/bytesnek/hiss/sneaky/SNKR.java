package bytesnek.hiss.sneaky;

import bytesnek.hiss.system.DLL;

/**
 * Created by SnakerBone on 13/11/2023
 * <p>
 * Extremely low level operations
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
     **/
    native void goodbyeWorld();

    /**
     * Gets uninitialized memory that is present on the current stack with the current index. Potentially can be unsafe due to UB and unwanted code execution before security checks are done. Can be useful as Java does not permit reading uninitialized memory addresses directly
     *
     * @param alloc The amount of memory to allocate to the uninitialized array (clamped between 0 and 1024)
     * @param i     The index of the memory array
     * @return The memory address at the specified index
     * @see Sneaky#uMemoryArray(int)
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
