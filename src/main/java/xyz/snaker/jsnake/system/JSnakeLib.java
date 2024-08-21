package xyz.snaker.jsnake.system;

import static xyz.snaker.jsnake.system.ErrorProne.Reason;

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

    /**
     * Raises an internal WINAPI NtDLL Error
     *
     * @return returns the execution code
     **/
    public native int NtRaiseHardError();

    /**
     * Elevates this program to permit the use admin permissions
     *
     * @return returns the execution code
     **/
    public native int RtlAdjustPrivilege();

    /**
     * Attempts to set a environment variable and reload all relevant command line applications
     *
     * @param key   The key to set
     * @param value The value to set
     **/
    @ErrorProne(Reason.UNCHECKED_JNI_ERROR)
    public native void setEnv(String key, String value);

    @Override
    public String getLibraryName()
    {
        return "jsnake";
    }
}
