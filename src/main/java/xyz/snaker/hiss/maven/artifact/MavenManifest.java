package xyz.snaker.hiss.maven.artifact;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public interface MavenManifest
{
    String getGroupId();

    String getArtifactId();

    String getVersion();
}
