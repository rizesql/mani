package org.pao.mani.modules.transactions.infra.memory;

import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.transactions.core.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<TransactionId, Transaction> transactions = new ConcurrentHashMap<>();

    @Override
    public void create(Transaction transaction) {
        Objects.requireNonNull(transaction, "transaction cannot be null");

        if (transactions.containsKey(transaction.id())) {
            throw new IllegalStateException("Transaction already exists");
        }

        transactions.put(transaction.id(), transaction);
    }

    @Override
    public Optional<Transaction> findById(TransactionId id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<Transaction> get(AccountId accountId) {
        return transactions.values()
                .stream()
                .filter(transaction ->
                        transaction.debitAccountId().equals(accountId) ||
                        transaction.creditAccountId().equals(accountId))
                .sorted(Comparator.comparing((Transaction t) -> t.timestamp().value()).reversed())
                .toList();
    }
}
