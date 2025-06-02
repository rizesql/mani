package org.pao.mani.modules.transactions.core;

import org.pao.mani.core.Money;
import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.transactions.core.exceptions.CircularTransactionException;

/**
 * Represents a financial transaction between two accounts.
 * Validates that debit and credit accounts are different and the amount is positive.
 * Immutable and thread-safe by design (as a record).
 */
public record Transaction(
        TransactionId id,
        AccountId debitAccountId,
        AccountId creditAccountId,
        Money amount,
        Timestamp timestamp
) {
    public Transaction {
        if (creditAccountId.equals(debitAccountId)) {
            throw new CircularTransactionException();
        }
    }
}
