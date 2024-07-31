package xyz.snaker.jsnake.repo;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import xyz.snaker.jsnake.logger.LogColour;
import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.repo.artifact.Artifact;
import xyz.snaker.jsnake.system.ProtocolSecurityType;
import xyz.snaker.jsnake.system.SystemSecretsAction;
import xyz.snaker.jsnake.utility.HttpUtilities;
import xyz.snaker.jsnake.utility.IOUtilities;

import java.io.*;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class RepositoryHook
{
    public static final Logger LOGGER = Loggers.getLogger();

    public static void main(String[] args)
    {
        RepositoryHook.assertUsingSupportedOS();
        IOUtilities.setIOProperties();
        RepositoryHook.printArgs(args);
        RepositoryHook.printCommandLineLogo();

        OptionSet set = OptionSpecs.getOptionSet(args);

        HttpUtilities.stopServerIfNeeded(OptionSpecs.HOSTNAME.value(set), OptionSpecs.PORT.value(set));

        Repository repository = createReposiliteMavenRepostory(OptionSpecs.NAME.value(set), OptionSpecs.USERNAME.value(set), OptionSpecs.PASSWORD.value(set), OptionSpecs.HOSTNAME.value(set), OptionSpecs.PORT.value(set));
        String repositoryName = repository.getName();
        String repositoryDirectory = String.format("%s\\%s", OptionSpecs.OUTPUT_DIRECTORY.value(set), repositoryName);
        File mavenOutput = new File(repositoryDirectory);

        if (OptionSpecs.GRADLE_PUBLISH_ENVIRONMENT.value(set)) {
            RepositoryHook.checkArgsForGradlePublishEnvironment(set);
            Artifact artifact = getPublishingArtifact(OptionSpecs.GROUP, OptionSpecs.ID, OptionSpecs.VERSION, set, repository.getHostInfo());

            repository.setPublishingArtifact(artifact);
        }

        repository.initialize(mavenOutput, "all", OptionSpecs.OPEN_IN_EXPLORER.value(set), OptionSpecs.OPEN_IN_BROWSER.value(set), OptionSpecs.INHERIT_IO.value(set), OptionSpecs.GRADLE_PUBLISH_ENVIRONMENT.value(set));

        Thread thread = getShutdownThread(repository);

        Runtime.getRuntime().addShutdownHook(thread);
    }

    static Thread getShutdownThread(Repository repository)
    {
        return new Thread("JSnake Shutdown Thread")
        {
            @Override
            public void run()
            {
                LOGGER.debug(String.format("Running %s", getName()));

                repository.getProcess().destroy();
            }
        };
    }

    static void checkArgsForGradlePublishEnvironment(OptionSet set)
    {
        if (!set.has(OptionSpecs.GROUP)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--group' or '--g'");
        } else if (!set.has(OptionSpecs.ID)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--id' or '--i");
        } else if (!set.has(OptionSpecs.VERSION)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--version' or '--v'");
        }
    }

    static Artifact getPublishingArtifact(OptionSpec<String> groupOption, OptionSpec<String> idOption, OptionSpec<String> versionOption, OptionSet set, HostInfo hostInfo)
    {
        String group = groupOption.value(set);
        String id = idOption.value(set);
        String version = versionOption.value(set);

        if (group == null || group.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --group JVM arg");
        } else if (id == null || id.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --id JVM arg");
        } else if (version == null || version.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --version JVM arg");
        }

        return new Artifact.Builder(hostInfo.getAddress())
                .setGroupId(group)
                .setArtifactId(id)
                .setVersion(version)
                .build();
    }

    static Repository createReposiliteMavenRepostory(String name, String username, String password, String hostname, int port)
    {
        Artifact artifact = getLatestReposiliteArtifact();
        HostInfo hostInfo = new HostInfo(ProtocolSecurityType.VULNERABLE, hostname, String.valueOf(port), SystemSecretsAction.NONE);
        Credentials credentials = new Credentials(username, password, SystemSecretsAction.NONE);

        return new Repository(name, credentials, hostInfo, artifact);
    }

    static Artifact getLatestReposiliteArtifact()
    {
        Artifact.Builder builder = new Artifact.Builder("https://maven.reposilite.com/releases");

        builder.setGroupId("com.reposilite");
        builder.setArtifactId("reposilite");
        builder.useLatestVersion();

        return builder.build();
    }

    static void printArgs(String[] args)
    {
        System.out.printf("Started with program args: %s%n", String.join(" ", args));
    }

    static void assertUsingSupportedOS()
    {
        String osName = System.getProperty("os.name").toLowerCase();

        if (!osName.contains("win")) {
            throw new UnsupportedOperationException("Unsupported OS");
        }
    }

    static String getMavenEnv(String key)
    {
        String name = String.format("MVN_%s", key.toUpperCase());

        return System.getenv(name);
    }

    static void printCommandLineLogo()
    {
        ClassLoader loader = RepositoryHook.class.getClassLoader();

        try {
            InputStream stream = loader.getResourceAsStream("repos_logo");

            if (stream == null) {
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

            while ((line = reader.readLine()) != null) {
                IOUtilities.printWithColour(line, System.out, LogColour.BLUE);
            }
        } catch (IOException ignored) {

        }
    }
}
