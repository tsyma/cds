package net.lafox.cds.service.exceptions;

import java.util.Collection;

import net.lafox.cds.utils.IpAddressMatcher;

public class BannedIpException extends RuntimeException {
    public BannedIpException(String ip, Collection<IpAddressMatcher> allowed) {
        super("IP '" + ip + "' not in allowed networks: " + allowed);
    }
}
