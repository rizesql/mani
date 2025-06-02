package org.pao.mani.modules.transactions.web;

import org.pao.mani.modules.transactions.core.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID debitAccountId,
        UUID creditAccountId,
        BigDecimal amount,
        String currency,
        String timestamp
) {
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.id().value(),
                transaction.debitAccountId().value(),
                transaction.creditAccountId().value(),
                transaction.amount().amount(),
                transaction.amount().currency().into(),
                transaction.timestamp().toInstant().toString()
        );
    }
}
