package xyz.snaker.jsnake.system;

import xyz.snaker.jsnake.sneaky.Sneaky;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by SnakerBone on 08/08/24
 * <p>
 * Licensed under MIT
 **/
public class PropertyRegistryManager
{
    private final Properties props = System.getProperties();
    private final String[] splitters;

    public PropertyRegistryManager(String mainSplitter, String... moreSplitters)
    {
        this.splitters = addSplitters(mainSplitter, moreSplitters);
    }

    private static String[] addSplitters(String main, String[] more)
    {
        List<String> splitters = new ArrayList<>(List.of(main));

        if (more.length > 0) {
            splitters.addAll(List.of(more));
        }

        return splitters.toArray(String[]::new);
    }

    public String setProperty(String key, String value)
    {
        String prop = (String) props.setProperty(key, value);
        Sneaky.fence(Sneaky.FenceAction.STORE_ONLY);
        return prop;
    }

    public String getProperty(String key)
    {
        String prop = props.getProperty(key);
        Sneaky.fence(Sneaky.FenceAction.LOAD_ONLY);
        return prop;
    }

    public String getSplitter()
    {
        return getSplitter(0);
    }

    public String getSplitter(int index)
    {
        return getSplitters()[index];
    }

    public String[] getSplitters()
    {
        return splitters;
    }
}
