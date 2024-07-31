package xyz.snaker.jsnake.repo;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.repo.artifact.Artifact;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiFunction;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class Repository
{
    public static final Logger LOGGER = Loggers.getLogger();

    private final String name;
    private final Credentials credentials;
    private final HostInfo hostInfo;
    private final Artifact artifact;
    private Artifact publishingArtifact;

    private final BiFunction<String, String, ProcessBuilder> processBuilderFunction = (path, token) -> new ProcessBuilder("java", "-Xmx64M", "-jar", path, "--token", token);
    private Process process;

    public Repository(String name, Credentials credentials, HostInfo hostInfo, Artifact artifact)
    {
        this.name = name;
        this.credentials = credentials;
        this.hostInfo = hostInfo;
        this.artifact = artifact;
    }

    public void initialize(File outputFolder, @Nullable String classifier, boolean openInFileExplorer, boolean openInBrowser, boolean inheritIO, boolean gradlePublishEnvironment)
    {
        Path path = Path.of(outputFolder.getAbsolutePath());
        String jarPath = path.toAbsolutePath() + "\\" + artifact.getJarName(classifier);
        String authToken = credentials.getToken();
        ProcessBuilder builder = processBuilderFunction.apply(jarPath, authToken);

        artifact.downloadJar(classifier, new File(jarPath));

        try {
            String hostAddress = hostInfo.getAddress();

            if (hostAddress.contains("0.0.0.0")) {
                hostAddress = hostAddress.replace("0.0.0.0", "127.0.0.1");
            }

            if (inheritIO) {
                builder.inheritIO();
            }

            builder.directory(path.toFile());

            process = builder.start();

            LOGGER.debugf("Initialized repository @ []", hostAddress);

            if (credentials.isValid()) {
                LOGGER.debugf("Started server as user '[]'", credentials.getUsername());
            }

            if (openInBrowser && gradlePublishEnvironment) {
                String command = String.format("explorer %s/#/releases/%s/%s/%s", hostAddress, publishingArtifact.getGroupId().replace('.', '/'), publishingArtifact.getArtifactId(), publishingArtifact.getVersion().getId());
                Runtime.getRuntime().exec(command);
                LOGGER.debug("Opened publsihed artifact in default browser");
            } else if (openInBrowser) {
                String command = String.format("explorer %s/#/", hostAddress);
                Runtime.getRuntime().exec(command);
                LOGGER.debug("Opened repository in default browser");
            }

            if (openInFileExplorer) {
                String command = String.format("explorer %s", path);
                Runtime.getRuntime().exec(command);
                LOGGER.debug("Opened repository in file explorer");
            }

            if (inheritIO) {
                LOGGER.debugf("Type 'stop' to terminate the server");
            }

            if (!gradlePublishEnvironment) {
                process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPublishingArtifact(Artifact publishingArtifact)
    {
        this.publishingArtifact = publishingArtifact;
    }

    public String getName()
    {
        return name;
    }

    public Credentials getCredentials()
    {
        return credentials;
    }

    public HostInfo getHostInfo()
    {
        return hostInfo;
    }

    public Artifact getArtifact()
    {
        return artifact;
    }

    public Artifact getPublishingArtifact()
    {
        return publishingArtifact;
    }

    public Process getProcess()
    {
        return process;
    }
}
