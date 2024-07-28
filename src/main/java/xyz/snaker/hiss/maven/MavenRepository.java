package xyz.snaker.hiss.maven;

import xyz.snaker.hiss.maven.artifact.MavenArtifact;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class MavenRepository
{
    private final String name;
    private final MavenCredentials credentials;
    private final MavenHostInfo hostInfo;
    private final MavenArtifact artifact;

    public MavenRepository(String name, MavenCredentials credentials, MavenHostInfo hostInfo, MavenArtifact artifact)
    {
        this.name = name;
        this.credentials = credentials;
        this.hostInfo = hostInfo;
        this.artifact = artifact;
        this.initialize();
    }

    public void initialize()
    {
        Path path = Paths.get("", "repository");

        artifact.downloadJar("all", path.toFile());

        String jarPath = path.resolve(artifact.getJarName("all")).toAbsolutePath().toString();
        String authToken = credentials.getToken();

        try {
            ProcessBuilder builder = new ProcessBuilder("java", "-Xmx64M", "-jar", jarPath, "--token", authToken);
            //builder.inheritIO();
            builder.directory(path.toFile());
            builder.start();
            Runtime.getRuntime().exec("explorer %s/#/".formatted(hostInfo.getAddress()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName()
    {
        return name;
    }

    public MavenCredentials getCredentials()
    {
        return credentials;
    }

    public MavenHostInfo getHostInfo()
    {
        return hostInfo;
    }

    public MavenArtifact getArtifact()
    {
        return artifact;
    }
}
