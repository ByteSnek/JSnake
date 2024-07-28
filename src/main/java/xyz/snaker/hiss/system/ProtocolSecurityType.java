package xyz.snaker.hiss.system;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public enum ProtocolSecurityType
{
    VULNERABLE(0, "http"),
    SECURE(1, "https");

    private final int id;
    private final String protocol;

    ProtocolSecurityType(int id, String protocol)
    {
        this.id = id;
        this.protocol = protocol;
    }

    public boolean isSecure()
    {
        return this == SECURE;
    }

    public int getId()
    {
        return id;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getFormattedProtocol()
    {
        return getProtocol() + "://";
    }
}
