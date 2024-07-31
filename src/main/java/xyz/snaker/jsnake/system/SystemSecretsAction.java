package xyz.snaker.jsnake.system;

/**
 * Created by SnakerBone on 31/07/24
 * <p>
 * Licensed under MIT
 **/
public enum SystemSecretsAction
{
    NONE("None"),
    USE_PROPS("Using Java System Properties"),
    USE_ENV("Using Environment Variables");

    private final String description;

    SystemSecretsAction(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
