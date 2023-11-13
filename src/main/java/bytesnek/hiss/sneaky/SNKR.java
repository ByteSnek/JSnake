package bytesnek.hiss.sneaky;

import bytesnek.hiss.system.DLL;

/**
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
     **/
    public native void goodbyeWorld();

    /**
     * Gets uninitialized memory that is present on the current stack with the current index. Potentially can be unsafe due to UB and unwanted code execution before security checks are done. Can be useful as Java does not permit reading uninitialized memory addresses directly
     *
     * @param alloc The amount of memory to allocate to the uninitialized array (clamped between 0 and 1024)
     * @param i     The index of the memory array
     * @return The memory address at the specified index
     * @see Sneaky#uMemoryArray(int)
     **/
    public native long uMemory(int alloc, int i);
}