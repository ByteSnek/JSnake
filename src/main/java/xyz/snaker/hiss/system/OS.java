package xyz.snaker.hiss.system;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public enum OS
{
    WINDOWS("win"),
    MAC("mac"),
    LINUX("linux"),
    UNKNOWN("unknown");

    private final String name;

    OS(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static OS identify()
    {
        String name = System.getProperty("os.name").toLowerCase();

        if (name.contains("win")) {
            return OS.WINDOWS;
        }

        if (name.contains("mac")) {
            return OS.MAC;
        }

        if (name.contains("linux")) {
            return OS.LINUX;
        }

        return UNKNOWN;
    }
}
