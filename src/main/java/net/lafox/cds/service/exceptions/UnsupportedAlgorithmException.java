package net.lafox.cds.service.exceptions;

public class UnsupportedAlgorithmException extends RuntimeException {

    public UnsupportedAlgorithmException(String s) {
        super("Unsupported Algorithm: " + s);
    }
}
