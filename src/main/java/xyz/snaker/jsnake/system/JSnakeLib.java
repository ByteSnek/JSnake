package xyz.snaker.jsnake.system;

/**
 * Created by SnakerBone on 31/07/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeLib implements DLL
{
    public JSnakeLib()
    {
        load(JSnakeLib.class, false);
    }

    public native int NtRaiseHardError();

    public native int RtlAdjustPrivilege();

    public native long getEarlyMemory(int alloc, int index, int min, int max);

    public native long[] getEarlyMemoryArray(int alloc, int min, int max);

    public native void setEnv(String key, String value);

    @Override
    public String getLibraryName()
    {
        return "jsnake";
    }
}
