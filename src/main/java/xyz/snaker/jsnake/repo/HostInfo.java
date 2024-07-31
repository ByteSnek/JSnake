package xyz.snaker.jsnake.repo;

import xyz.snaker.jsnake.system.ProtocolSecurityType;
import xyz.snaker.jsnake.system.SystemSecretsAction;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class HostInfo
{
    private final ProtocolSecurityType securityType;
    private final String hostname;
    private final String address;
    private final String port;

    public HostInfo(ProtocolSecurityType securityType, String hostname, String port, SystemSecretsAction action)
    {
        if (securityType == null) {
            throw new RuntimeException("Security type cannot be null");
        } else if (hostname == null) {
            throw new RuntimeException("Hostname cannot be null");
        } else if (String.valueOf(port).length() != 4) {
            throw new RuntimeException("Port must have at least 4 numbers");
        } else {
            this.securityType = securityType;

            if (action == SystemSecretsAction.NONE || action == null) {
                this.hostname = hostname;
                this.port = port;
                this.address = formatAddress(securityType, hostname, port);
            } else {
                switch (action) {
                    case USE_PROPS -> {
                        this.hostname = System.getProperty(hostname);
                        this.port = System.getProperty(port);
                        this.address = formatAddress(securityType, this.hostname, this.port);
                    }
                    case USE_ENV -> {
                        this.hostname = System.getenv(hostname);
                        this.port = System.getenv(port);
                        this.address = formatAddress(securityType, this.hostname, this.port);
                    }
                    default -> {
                        this.hostname = hostname;
                        this.port = port;
                        this.address = formatAddress(securityType, hostname, port);
                    }
                }
            }
        }
    }

    private static String formatAddress(ProtocolSecurityType securityType, String hostname, String port)
    {
        return String.format("%s%s:%s", securityType.getFormattedProtocol(), hostname, port);
    }

    public ProtocolSecurityType getSecurityType()
    {
        return securityType;
    }

    public String getHostname()
    {
        return hostname;
    }

    public String getAddress()
    {
        return address;
    }

    public String getAddressWithoutProtocol()
    {
        String protocol = securityType.getFormattedProtocol();

        return address.replace(protocol, "");
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
