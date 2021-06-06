package net.lafox.cds.service.exceptions;

public class ImageInfoException extends RuntimeException{
    public ImageInfoException(String pattern) {
        super("Wrong ImageInfo String: " + pattern);
    }
}
