package br.com.alx.exception;

public class InvestmentNotFoundException extends RuntimeException {
    public InvestmentNotFoundException(String message) {
        super(message);
    }
}
