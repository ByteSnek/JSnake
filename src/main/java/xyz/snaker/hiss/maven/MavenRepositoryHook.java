package xyz.snaker.hiss.maven;

import xyz.snaker.hiss.maven.artifact.MavenArtifact;
import xyz.snaker.hiss.system.ProtocolSecurityType;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class MavenRepositoryHook
{
    public static final String MAVEN_USERNAME_KEY = "maven.username";
    public static final String MAVEN_PASSWORD_KEY = "maven.password";
    public static final String MAVEN_HOST_KEY = "maven.hostname";
    public static final String MAVEN_PORT_KEY = "maven.port";

    public static void main(String[] args)
    {
        MavenRepositoryHook.defineProperties();
        MavenArtifact artifact = new MavenArtifact("https://maven.reposilite.com/releases", "com.reposilite", "reposilite", "3.5.14");
        MavenHostInfo info = new MavenHostInfo(ProtocolSecurityType.VULNERABLE, MAVEN_HOST_KEY, MAVEN_PORT_KEY);
        MavenCredentials credentials = new MavenCredentials(MAVEN_USERNAME_KEY, MAVEN_PASSWORD_KEY);

        new MavenRepository("snake", credentials, info, artifact);

        System.exit(0);
    }

    private static void defineProperties()
    {
        System.setProperty(MAVEN_USERNAME_KEY, getMavenEnv("user"));
        System.setProperty(MAVEN_PASSWORD_KEY, getMavenEnv("pass"));
        System.setProperty(MAVEN_HOST_KEY, getMavenEnv("host"));
        System.setProperty(MAVEN_PORT_KEY, getMavenEnv("port"));
    }

    private static String getMavenEnv(String key)
    {
        String name = String.format("MVN_%s", key.toUpperCase());

        return System.getenv(name);
    }
}
