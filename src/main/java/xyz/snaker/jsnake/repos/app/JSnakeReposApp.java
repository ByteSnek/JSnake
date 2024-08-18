package xyz.snaker.jsnake.repos.app;

import joptsimple.OptionSet;
import org.apache.commons.lang3.NotImplementedException;
import xyz.snaker.jsnake.logger.LogColour;
import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.repos.JSnakeOptionSpecRegistry;
import xyz.snaker.jsnake.repos.JSnakeRepository;
import xyz.snaker.jsnake.repos.artifact.JSnakeArtifact;
import xyz.snaker.jsnake.repos.data.JSnakeCredentials;
import xyz.snaker.jsnake.repos.data.JSnakeHostInfo;
import xyz.snaker.jsnake.repos.data.JSnakeProperties;
import xyz.snaker.jsnake.sneaky.Sneaky;
import xyz.snaker.jsnake.system.ProtocolSecurityType;
import xyz.snaker.jsnake.system.SystemSecretsAction;
import xyz.snaker.jsnake.utility.HttpUtilities;
import xyz.snaker.jsnake.utility.IOUtilities;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeReposApp
{
    public static final Logger LOGGER = Loggers.getLogger();

    private final String[] args;
    private final OptionSet optionSet;
    private final JSnakeRepository repository;

    public static void main(String[] args)
    {
        JSnakeReposApp app = JSnakeReposApp.getInstance(args);
        JSnakeRepository repository = app.getRepository();
        OptionSet set = app.getOptionSet();

        app.createReposiliteRepostory();

        String repositoryDirectory = app.getOrCreateRepositoryPath();
        File mavenOutput = new File(repositoryDirectory);

        if (JSnakeOptionSpecRegistry.GRADLE_PUBLISH_ENVIRONMENT.value(set)) {
            app.checkArgsForGradlePublishEnvironment();
            repository.setPublishingArtifact(app.getPublishingArtifact());
        }

        app.initialize(mavenOutput);

        Thread thread = app.getShutdownThread();
        Runtime.getRuntime().addShutdownHook(thread);
    }

    public static JSnakeReposApp getInstance(String[] args)
    {
        return new JSnakeReposApp(args);
    }

    private JSnakeReposApp(String[] args)
    {
        this.args = Objects.requireNonNullElse(args, Sneaky.tryGetInstanceArgs());
        this.optionSet = JSnakeOptionSpecRegistry.getOptionSet(args);
        this.repository = createReposiliteRepostory();
        this.preInitialize();
    }

    public void preInitialize()
    {
        if (!Sneaky.isWindowsOS()) {
            throw new NotImplementedException("JSnake Repos currently only work on Windows operating systems");
        }

        JSnakeProperties.initialize(args);
        System.out.printf("Started with program args: %s%n", String.join(" ", args));
        ClassLoader loader = JSnakeReposApp.class.getClassLoader();

        try {
            InputStream stream = loader.getResourceAsStream("repos_logo.jsnake");

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

    public void initialize(File repoFolder)
    {
        repository.initialize(repoFolder, JSnakeOptionSpecRegistry.OPEN_IN_EXPLORER, JSnakeOptionSpecRegistry.OPEN_IN_BROWSER, JSnakeOptionSpecRegistry.INHERIT_IO, JSnakeOptionSpecRegistry.GRADLE_PUBLISH_ENVIRONMENT, optionSet, "all");
    }

    public Thread getShutdownThread()
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

    public void checkArgsForGradlePublishEnvironment()
    {
        if (!optionSet.has(JSnakeOptionSpecRegistry.GROUP)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--group' or '--g'");
        } else if (!optionSet.has(JSnakeOptionSpecRegistry.ID)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--id' or '--i");
        } else if (!optionSet.has(JSnakeOptionSpecRegistry.VERSION)) {
            throw new RuntimeException("Missing required program argument for gradle publish environment: '--version' or '--v'");
        }
    }

    public JSnakeArtifact getPublishingArtifact()
    {
        String group = JSnakeOptionSpecRegistry.GROUP.value(optionSet);
        String id = JSnakeOptionSpecRegistry.ID.value(optionSet);
        String version = JSnakeOptionSpecRegistry.VERSION.value(optionSet);

        if (group == null || group.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --group JVM arg");
        } else if (id == null || id.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --id JVM arg");
        } else if (version == null || version.isEmpty()) {
            throw new RuntimeException("Could not publish artifact: Missing --version JVM arg");
        }

        return new JSnakeArtifact.Builder(repository.getHostInfo().getAddress())
                .setGroupId(group)
                .setArtifactId(id)
                .setVersion(version)
                .build();
    }

    public JSnakeRepository createReposiliteRepostory()
    {
        String name = JSnakeOptionSpecRegistry.NAME.value(optionSet);
        String username = JSnakeOptionSpecRegistry.USERNAME.value(optionSet);
        String password = JSnakeOptionSpecRegistry.PASSWORD.value(optionSet);
        String hostname = JSnakeOptionSpecRegistry.HOSTNAME.value(optionSet);
        Integer port = JSnakeOptionSpecRegistry.PORT.value(optionSet);

        HttpUtilities.stopServerIfNeeded(hostname, port);

        JSnakeArtifact artifact = getLatestReposiliteArtifact();
        JSnakeHostInfo hostInfo = new JSnakeHostInfo(ProtocolSecurityType.VULNERABLE, hostname, String.valueOf(port), SystemSecretsAction.NONE);
        JSnakeCredentials credentials = new JSnakeCredentials(username, password, SystemSecretsAction.NONE);

        return new JSnakeRepository(name, credentials, hostInfo, artifact);
    }

    public JSnakeArtifact getLatestReposiliteArtifact()
    {
        JSnakeArtifact.Builder builder = new JSnakeArtifact.Builder("https://maven.reposilite.com/releases");

        builder.setGroupId("com.reposilite");
        builder.setArtifactId("reposilite");
        builder.useLatestVersion();

        return builder.build();
    }

    public String getOrCreateRepositoryPath()
    {
        String key = "repo_path";
        String data = IOUtilities.getFromStorage(key);

        String outputPath = JSnakeOptionSpecRegistry.OUTPUT_DIRECTORY.value(optionSet);
        String repoPath = JSnakeOptionSpecRegistry.EXISTING_REPOSITORY_DIRECTORY.value(optionSet);

        String repoName = repository.getName();

        if (!repoPath.isEmpty()) {
            Path path = Path.of(repoPath).toAbsolutePath();
            IOUtilities.writeToStorage(path, key, true);
            return path.toString();
        }

        if (data == null) {
            String path = String.format("%s\\%s", outputPath, repoName);
            IOUtilities.writeToStorage(path, key, true);
            return path;
        }

        return data;
    }

    public String[] getArgs()
    {
        return args;
    }

    public JSnakeRepository getRepository()
    {
        return repository;
    }

    public OptionSet getOptionSet()
    {
        return optionSet;
    }
}
