package org.pao.mani.core.exceptions;

public class UnknownCurrencyException extends RuntimeException {
    public UnknownCurrencyException(String code) {
        super("Unknown currency code: " + code);
    }
}
