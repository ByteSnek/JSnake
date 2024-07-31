package xyz.snaker.jsnake.repo.artifact;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public interface Manifest
{
    String getGroupId();

    String getArtifactId();

    ArtifactVersion getVersion();
}
