package org.pao.mani.queries;

import org.pao.mani.data.TransactionRepository;
import org.pao.mani.domain.*;

import java.util.Optional;

public final class LookupTransactions {
    public record Query(Transaction.Id id) {}

    private final TransactionRepository transactionRepository;

    public LookupTransactions(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> run(Query query) {
        return transactionRepository.lookup(query.id());
    }
}
