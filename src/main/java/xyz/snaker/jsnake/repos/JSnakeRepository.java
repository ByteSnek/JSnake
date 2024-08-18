package xyz.snaker.jsnake.repos;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.repos.artifact.JSnakeArtifact;
import xyz.snaker.jsnake.repos.data.JSnakeCredentials;
import xyz.snaker.jsnake.repos.data.JSnakeHostInfo;

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
public class JSnakeRepository
{
    public static final Logger LOGGER = Loggers.getLogger();

    private final String name;
    private final JSnakeCredentials credentials;
    private final JSnakeHostInfo hostInfo;
    private final JSnakeArtifact artifact;

    private JSnakeArtifact publishingArtifact;

    private final BiFunction<String, String, ProcessBuilder> processBuilderFunction = (path, token) -> new ProcessBuilder("java", "-Xmx64M", "-jar", path, "--token", token);
    private Process process;

    public JSnakeRepository(String name, JSnakeCredentials credentials, JSnakeHostInfo hostInfo, JSnakeArtifact artifact)
    {
        this.name = name;
        this.credentials = credentials;
        this.hostInfo = hostInfo;
        this.artifact = artifact;
    }

    public void initialize(File outputFolder, OptionSpec<Boolean> openInExplorerSpec, OptionSpec<Boolean> openInBrowserSpec, OptionSpec<Boolean> inheritIOSpec, OptionSpec<Boolean> gradlePublishEnvironmentSpec, OptionSet set, @Nullable String classifier)
    {
        Boolean openInExplorer = openInExplorerSpec.value(set);
        Boolean openInBrowser = openInBrowserSpec.value(set);
        Boolean inheritIO = inheritIOSpec.value(set);
        Boolean gradlePublishEnvironment = gradlePublishEnvironmentSpec.value(set);

        initialize(outputFolder, classifier, openInExplorer, openInBrowser, inheritIO, gradlePublishEnvironment);
    }

    public void initialize(File outputFolder, @Nullable String classifier, boolean openInExplorer, boolean openInBrowser, boolean inheritIO, boolean gradlePublishEnvironment)
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

            if (openInExplorer) {
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

    public void setPublishingArtifact(JSnakeArtifact publishingArtifact)
    {
        this.publishingArtifact = publishingArtifact;
    }

    public String getName()
    {
        return name;
    }

    public JSnakeCredentials getCredentials()
    {
        return credentials;
    }

    public JSnakeHostInfo getHostInfo()
    {
        return hostInfo;
    }

    public JSnakeArtifact getArtifact()
    {
        return artifact;
    }

    public JSnakeArtifact getPublishingArtifact()
    {
        return publishingArtifact;
    }

    public Process getProcess()
    {
        return process;
    }
}
