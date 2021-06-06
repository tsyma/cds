package net.lafox.cds.service.exceptions;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String s) {
        super(s);
    }
}
