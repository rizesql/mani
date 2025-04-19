package org.pao.mani.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record Transaction(Id id, Account.Id debitAccountId, Account.Id creditAccountId, Money amount,
                          Timestamp timestamp) {
    public record Id(UUID id) {
        public Id {
            Objects.requireNonNull(id, "Id cannot be null");
        }

        public static Transaction.Id generate() {
            return new Id(UUID.randomUUID());
        }
    }

    public Transaction {
        if (creditAccountId.equals(debitAccountId)) {
            throw new IllegalArgumentException("Debit account cannot be the same");
        }

        if (amount.amount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
