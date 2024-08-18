package xyz.snaker.jsnake.repos.artifact;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public interface JSnakeManifest
{
    String getGroupId();

    String getArtifactId();

    JSnakeArtifactVersion getVersion();
}
