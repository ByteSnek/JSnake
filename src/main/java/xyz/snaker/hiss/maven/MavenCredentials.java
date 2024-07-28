package xyz.snaker.hiss.maven;

import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class MavenCredentials
{
    public static final MavenCredentials UNAUTHORIZED = new MavenCredentials(null, null);

    private final String username;
    private final String password;
    private final String token;

    public MavenCredentials(@Nullable String usernameKey, @Nullable String passwordKey)
    {
        if (usernameKey != null && passwordKey != null) {
            this.username = System.getProperty(usernameKey);
            this.password = System.getProperty(passwordKey);
            this.token = String.format("%s:%s", username, password);
        } else {
            this.username = null;
            this.password = null;
            this.token = null;
        }
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
