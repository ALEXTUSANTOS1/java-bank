package br.com.alx.exception;

public class PixInUseException extends RuntimeException {
    public PixInUseException(String message) {
        super(message);
    }
}
