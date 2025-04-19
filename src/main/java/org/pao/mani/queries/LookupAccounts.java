package org.pao.mani.queries;

import org.pao.mani.data.AccountRepository;
import org.pao.mani.domain.*;

import java.util.Optional;

public final class LookupAccounts {
    public record Query(Account.Id id) {
    }

    private final AccountRepository accountRepository;

    public LookupAccounts(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> run(Query query) {
        return accountRepository.lookup(query.id());
    }
}
