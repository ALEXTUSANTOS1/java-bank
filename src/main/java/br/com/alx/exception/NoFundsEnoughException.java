package br.com.alx.exception;

public class NoFundsEnoughException extends RuntimeException {
    public NoFundsEnoughException(String message) {
        super(message);
    }
}
