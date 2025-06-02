package org.pao.mani.modules.transactions.core;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a Transaction.
 */
public record TransactionId(UUID value) {
    public TransactionId {
        Objects.requireNonNull(value, "Id cannot be null");
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }
}
