package org.pao.mani.queries;

import org.pao.mani.data.TransactionRepository;
import org.pao.mani.domain.*;
import org.pao.mani.queries.utils.TimestampRange;

import java.util.List;

public final class GetTransactions {
    public record Query(Account.Id accountId, TimestampRange within, int limit) {
    }

    private final TransactionRepository transactionRepository;

    public GetTransactions(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> run(Query query) {
        return transactionRepository
                .get(query.accountId())
                .stream()
                .filter(t -> query.within().includes(t.timestamp()))
                .limit(query.limit())
                .toList();
    }
}
