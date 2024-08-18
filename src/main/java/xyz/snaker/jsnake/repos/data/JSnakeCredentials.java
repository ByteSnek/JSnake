package xyz.snaker.jsnake.repos.data;

import xyz.snaker.jsnake.system.SystemSecretsAction;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class JSnakeCredentials
{
    public static final JSnakeCredentials UNAUTHORIZED = new JSnakeCredentials(null, null, SystemSecretsAction.NONE);

    private final String username;
    private final String password;
    private final String token;

    public JSnakeCredentials(@Nullable String username, @Nullable String password, SystemSecretsAction action)
    {
        if (username == null || password == null) {
            this.username = null;
            this.password = null;
            this.token = formatToken(null, null);
        } else {
            if (action == SystemSecretsAction.NONE || action == null) {
                this.username = username;
                this.password = password;
                this.token = formatToken(username, password);
            } else {
                switch (action) {
                    case USE_PROPS -> {
                        this.username = System.getProperty(username);
                        this.password = System.getProperty(password);
                        this.token = formatToken(this.username, this.password);
                    }
                    case USE_ENV -> {
                        this.username = System.getenv(username);
                        this.password = System.getenv(password);
                        this.token = formatToken(this.username, this.password);
                    }
                    default -> {
                        this.username = username;
                        this.password = password;
                        this.token = formatToken(username, password);
                    }
                }
            }
        }
    }

    private static String formatToken(String username, String password)
    {
        return String.format("%s:%s", Objects.requireNonNullElse(username, "null"), Objects.requireNonNullElse(password, "null"));
    }

    public boolean isValid()
    {
        return username != null && password != null && token != null;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getToken()
    {
        return token;
    }

    @Override
    public String toString()
    {
        return token;
    }
}
