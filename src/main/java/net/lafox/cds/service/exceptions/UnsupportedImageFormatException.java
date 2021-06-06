package net.lafox.cds.service.exceptions;

import java.util.Set;

import net.lafox.cds.data.ImageInfo;

public class UnsupportedImageFormatException extends RuntimeException {
    public UnsupportedImageFormatException(ImageInfo imageInfo, Set<ImageInfo> acceptableFormats) {
        super("Unsupported Image Format: '" + imageInfo + "'. Please use one of: " + acceptableFormats );

    }
}
