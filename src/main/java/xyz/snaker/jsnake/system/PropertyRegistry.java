package xyz.snaker.jsnake.system;

/**
 * Created by SnakerBone on 08/08/24
 * <p>
 * Licensed under MIT
 **/
public class PropertyRegistry
{
    public static final PropertyRegistryManager MANAGER = new PropertyRegistryManager(">>");

    public static final String JSNAKE_STORAGE_FOLDER = register(
            "jsnake.storage",
            String.format("%s\\jsnake", System.getProperty("java.io.tmpdir"))
    );
    public static final String JSNAKE_CHROMEDRIVER_FOLDER = register(
            "jsnake.chromedriver",
            String.format("%s\\chromedriver", JSNAKE_STORAGE_FOLDER)
    );

    public static String register(String key, String value)
    {
        return register(String.format("%s%s%s", key, MANAGER.getSplitter(), value));
    }

    public static String register(String entry)
    {
        String splitter = MANAGER.getSplitter();

        if (!entry.contains(splitter)) {
            throw new RuntimeException("Entry string must contain a splitter");
        } else {
            String[] parts = entry.split(splitter);

            if (parts.length != 2) {
                throw new RuntimeException("Entry string must contain only 1 splitter");
            } else {
                String key = parts[0];
                String value = parts[1];

                return MANAGER.setProperty(key, value);
            }
        }
    }
}
