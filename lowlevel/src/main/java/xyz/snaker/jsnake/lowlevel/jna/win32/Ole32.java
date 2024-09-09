package xyz.snaker.jsnake.lowlevel.jna.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Created by SnakerBone on 08/09/24
 **/
public class Ole32
{
    static {
        Native.register("ole32");
    }

    public static native Pointer OleInitialize(Pointer pvReserved);

    public static native void CoTaskMemFree(Pointer pv);
}
