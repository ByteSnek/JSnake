package xyz.snaker.jsnake.repos.artifact;

/**
 * Created by SnakerBone on 29/07/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeArtifactVersion
{
    private final String id;

    private JSnakeArtifactVersion(String id)
    {
        this.id = id;
    }

    public static JSnakeArtifactVersion of(String id)
    {
        return new JSnakeArtifactVersion(id);
    }

    public String getId()
    {
        return id;
    }
}
