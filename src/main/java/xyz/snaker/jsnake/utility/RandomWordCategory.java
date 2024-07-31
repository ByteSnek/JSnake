package xyz.snaker.jsnake.utility;

/**
 * Created by SnakerBone on 30/07/24
 * <p>
 * Licensed under MIT
 **/
public enum RandomWordCategory
{
    NOUN("/random/noun"),
    ADJECTIVE("/random/adjective"),
    ANIMAL("/random/animal");

    private final String path;

    RandomWordCategory(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

    @Override
    public String toString()
    {
        return path;
    }
}
