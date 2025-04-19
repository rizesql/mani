package org.pao.mani.queries;

import org.pao.mani.data.AccountRepository;
import org.pao.mani.data.TransactionRepository;
import org.pao.mani.domain.*;
import org.pao.mani.queries.utils.TimestampRange;

public final class AccountBalance {

    public record Query(Account.Id id, TimestampRange within) {
    }

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountBalance(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Balance run(Query query) {
        var account = accountRepository.lookup(query.id()).orElseThrow().activeOrThrow();

        return Balance.from(
                account,
                transactionRepository.get(query.id())
                        .stream()
                        .filter(t -> query.within().includes(t.timestamp()))
                        .toList()
        );

    }
}
