package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.*;

public final class InMemoryTransactionRepository implements TransactionRepository {
    private final HashMap<Transaction.Id, Transaction> transactions = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        Objects.requireNonNull(transaction, "transaction cannot be null");

        if (transactions.containsKey(transaction.id())) {
            throw new IllegalStateException("Transaction already exists");
        }

        transactions.put(transaction.id(), transaction);
    }

    @Override
    public Optional<Transaction> lookup(Transaction.Id id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<Transaction> get(Account.Id id) {
        return transactions
                .values()
                .stream()
                .filter(t -> t.creditAccountId().equals(id) || t.debitAccountId().equals(id))
                .toList();
    }
}
