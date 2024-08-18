package xyz.snaker.jsnake.repos.data;

/**
 * Created by SnakerBone on 01/08/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeProperties
{
    public static final String KEY_JSNAKE_STORAGE = "jsnake.storage";
    public static final String KEY_JSNAKE_ARGS = "jsnake.args";

    public static void initialize(String[] args)
    {
        String tmpdir = System.getProperty("java.io.tmpdir");

        String jsnakeStorage = String.format("%s\\jsnake", tmpdir);
        System.setProperty(KEY_JSNAKE_STORAGE, jsnakeStorage);

        if (args != null) {
            String jsnakeArgs = String.join(" ", args);
            System.setProperty(KEY_JSNAKE_ARGS, jsnakeArgs);
        } else {
            System.setProperty(KEY_JSNAKE_ARGS, "");
        }
    }
}
