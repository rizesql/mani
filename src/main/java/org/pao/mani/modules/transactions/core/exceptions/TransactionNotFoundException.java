package org.pao.mani.modules.transactions.core.exceptions;

import org.pao.mani.modules.transactions.core.TransactionId;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(TransactionId id) {
        super("Transaction not found: " + id.value());
    }
}
