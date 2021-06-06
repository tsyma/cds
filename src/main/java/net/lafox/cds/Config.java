package net.lafox.cds;

import static net.lafox.cds.utils.FileSystemUtils.mkdirIfNotExists;
import static net.lafox.cds.utils.StreamHelper.splitAsStream;
import static net.lafox.cds.utils.SystemUtils.getUserHome;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import javax.annotation.PostConstruct;

import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.utils.ImageInfoBuilder;
import net.lafox.cds.utils.IpAddressMatcher;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Getter
@Slf4j
@ToString
public class Config {
    public static final String SUPPORTED_TYPES = "png|jpg|gif";
    public static final int ID_LENGTH = 5;
    public static final int CREATE_ID_MAX_ATTEMPTS = 16;

    private final String serverToken;
    private final String patchOriginal;
    private final String patchConverted;
    private final Set<ImageInfo> acceptableFormats;
    private final Set<IpAddressMatcher> acceptableNetworks;

    public Config(
            @Value("${cds.server-token}") String serverToken,
            @Value("${cds.path-original:~/cds/cds-original}") String patchOriginal,
            @Value("${cds.path-converted:~/cds/cds-converted}") String patchConverted,
            @Value("${cds.acceptable-formats:c100x100q50.jpg, c200x200q50.jpg, c300x300q50.jpg}") String allowedFormats,
            @Value("${cds.acceptable-networks:127.0.0.0/8, 192.168.0.0/16, 10.0.0.0/8, 172.16.0.0/12}") String allowedNetworks
    ) {
        this.serverToken = serverToken;
        this.patchOriginal = parsePath(patchOriginal);
        this.patchConverted = parsePath(patchConverted);
        this.acceptableFormats = parseImageInfos(allowedFormats);
        this.acceptableNetworks = parseNetworks(allowedNetworks);
    }


    @PostConstruct
    private void postConstruct() {
        mkdirIfNotExists(patchOriginal);
        mkdirIfNotExists(patchConverted);
        log.info(toString());
    }

    private String parsePath(String path) {
        return path.replaceFirst("^~", getUserHome());
    }

    private Set<IpAddressMatcher> parseNetworks(String networksString) {
        return splitAsStream(networksString)
                .map(IpAddressMatcher::new)
                .collect(toSet());
    }

    private Set<ImageInfo> parseImageInfos(String acceptableFormats) {
        return splitAsStream(acceptableFormats)
                .map(ImageInfoBuilder::parseImageInfoString)
                .collect(toSet());
    }

}
