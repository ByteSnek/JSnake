package xyz.snaker.jsnake.repo.artifact;

/**
 * Created by SnakerBone on 29/07/24
 * <p>
 * Licensed under MIT
 **/
public class ArtifactVersion
{
    private final String id;

    private ArtifactVersion(String id)
    {
        this.id = id;
    }

    public static ArtifactVersion of(String id)
    {
        return new ArtifactVersion(id);
    }

    public String getId()
    {
        return id;
    }
}
