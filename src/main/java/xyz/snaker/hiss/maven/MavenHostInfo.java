package xyz.snaker.hiss.maven;

import xyz.snaker.hiss.system.ProtocolSecurityType;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class MavenHostInfo
{
    private final ProtocolSecurityType securityType;
    private final String host;
    private final String address;
    private final String port;

    public MavenHostInfo(ProtocolSecurityType securityType, String hostnameKey, String portKey)
    {
        this.securityType = securityType;
        this.host = System.getProperty(hostnameKey);
        this.port = System.getProperty(portKey);
        this.address = formatAddress();
    }

    private String formatAddress()
    {
        return String.format("%s%s:%s", securityType.getFormattedProtocol(), host, port);
    }

    public ProtocolSecurityType getSecurityType()
    {
        return securityType;
    }

    public String getHost()
    {
        return host;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPort()
    {
        return port;
    }

    @Override
    public String toString()
    {
        return address;
    }
}
