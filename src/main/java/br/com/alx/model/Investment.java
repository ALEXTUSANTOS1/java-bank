package br.com.alx.model;

public record Investment(
        long id,
        long tax,
        long daysToRescue,
        long initialFunds
) {
}
