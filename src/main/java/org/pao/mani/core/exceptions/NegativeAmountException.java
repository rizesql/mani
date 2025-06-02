package org.pao.mani.core.exceptions;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount must be greater than zero");
    }
}
