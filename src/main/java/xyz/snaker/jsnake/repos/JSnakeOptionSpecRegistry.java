package xyz.snaker.jsnake.repos;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import xyz.snaker.jsnake.sneaky.Sneaky;

import java.util.List;

/**
 * Created by SnakerBone on 31/07/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeOptionSpecRegistry
{
    public static final OptionParser PARSER = new OptionParser();
    public static final String USER_HOME = System.getProperty("user.home");

    public static final OptionSpec<String> NAME = register(
            JSnakeOptionSpecRegistry.getRandomRepositoryName(),
            "Sets the name of the repository",
            "name", "n"
    );
    public static final OptionSpec<String> EXISTING_REPOSITORY_DIRECTORY = register(
            "",
            "Sets an existing path to a reposilite repository to use",
            "from", "f"
    );
    public static final OptionSpec<String> OUTPUT_DIRECTORY = register(
            String.format("%s\\JSnakeRepos", USER_HOME),
            "Sets the folder the repository will be stored",
            "output", "o"
    );
    public static final OptionSpec<Boolean> OPEN_IN_EXPLORER = register(
            true,
            "Opens the repository folder in windows explorer",
            "openInExplorer", "oix"
    );
    public static final OptionSpec<Boolean> OPEN_IN_BROWSER = register(
            true,
            "Opens the repository website in the default browser",
            "openInBrowser", "oib"
    );
    public static final OptionSpec<Boolean> GRADLE_PUBLISH_ENVIRONMENT = register(
            false,
            "Sets the gradle publish environment",
            "gradlePublishEnvironment", "gpe"
    );
    public static final OptionSpec<Boolean> INHERIT_IO = register(
            false,
            "Sets whether reposilite should inherit it's IO to the active terminal",
            "inheritIO", "io"
    );

    public static final OptionSpec<String> GROUP = register(
            "",
            "Sets the publishing artifact's group id",
            "group", "g"
    );
    public static final OptionSpec<String> ID = register(
            "",
            "Sets the publishing artifact's project id",
            "id", "i"
    );
    public static final OptionSpec<String> VERSION = register(
            "",
            "Sets the publishing artifact's version",
            "version", "v"
    );

    public static final OptionSpec<String> HOSTNAME = register(
            "0.0.0.0",
            "Sets the host address for reposilite to be hosted on",
            "hostname", "h"
    );
    public static final OptionSpec<Integer> PORT = register(
            8080,
            "Sets the port for reposilite to be hosted on",
            "port", "p"
    );

    public static final OptionSpec<String> USERNAME = register(
            "admin",
            "Sets the reposilite sign in username",
            "username", "U"
    );
    public static final OptionSpec<String> PASSWORD = register(
            "1234",
            "Sets the reposilite sign in password",
            "password", "P"
    );

    public static OptionSet getOptionSet(String[] args)
    {
        return PARSER.parse(args);
    }

    static <V> OptionSpec<V> register(V defaultValue, String description, String... params)
    {
        List<String> list = List.of(params);

        return Sneaky.cast(
                PARSER.acceptsAll(list, description)
                        .withRequiredArg()
                        .ofType(Sneaky.cast(defaultValue.getClass()))
                        .defaultsTo(defaultValue)
        );
    }

    static String getRandomRepositoryName()
    {
        return "JSnakeReposApp.getExtendedFaker().placeholder();";
    }
}
