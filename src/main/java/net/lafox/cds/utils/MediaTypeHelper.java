package net.lafox.cds.utils;

import org.springframework.http.MediaType;

public class MediaTypeHelper {
    public static MediaType getMediaType(String fileExtension) {
        switch (fileExtension) {
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;

            default:
                throw new RuntimeException("Unsupported type!" + fileExtension);
        }
    }
}
