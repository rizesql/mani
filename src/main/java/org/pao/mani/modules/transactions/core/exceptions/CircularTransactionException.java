package org.pao.mani.modules.transactions.core.exceptions;

public class CircularTransactionException extends RuntimeException {
    public CircularTransactionException() {
        super("Circular transaction");
    }
}
