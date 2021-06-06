package net.lafox.cds.service;

import javax.servlet.http.HttpServletRequest;

import net.lafox.cds.Config;
import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.utils.IpAddressMatcher;
import net.lafox.cds.service.exceptions.BannedIpException;
import net.lafox.cds.service.exceptions.UnsupportedImageFormatException;
import net.lafox.cds.service.exceptions.WrongTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final Config config;

    public void validateToken(HttpServletRequest request) {
        String token = request.getHeader("Token");
        if (!config.getServerToken().equals(token)) {
            throw new WrongTokenException("Wrong token!");
        }
    }

    public void validateImageFormat(ImageInfo imageInfo) {
        ImageInfo format = imageInfo.toBuilder().id(null).build();
        if (!config.getAcceptableFormats().contains(format)) {
            throw new UnsupportedImageFormatException(format, config.getAcceptableFormats());
        }
    }

    public void validateIpAddress(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        for (IpAddressMatcher ipAddressMatcher : config.getAcceptableNetworks()) {
            if (ipAddressMatcher.matches(ip)) {
                return;
            }
        }
        throw new BannedIpException(ip, config.getAcceptableNetworks());
    }
}
