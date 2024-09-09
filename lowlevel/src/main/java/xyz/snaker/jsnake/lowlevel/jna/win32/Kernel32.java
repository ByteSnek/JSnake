package xyz.snaker.jsnake.lowlevel.jna.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Created by SnakerBone on 09/09/24
 **/
public class Kernel32
{
    static {
        Native.register("kernel32");
    }

    public static native Pointer GetModuleHandle(String lpModuleName);

    public static native Pointer GetProcAddress(Pointer hModule, String lpProcName);
}
