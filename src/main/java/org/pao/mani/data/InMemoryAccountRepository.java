package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.HashMap;
import java.util.Optional;

public final class InMemoryAccountRepository implements AccountRepository {
    private final HashMap<Account.Id, Account> accounts = new HashMap<>();

    @Override
    public void save(Account.Active account) {
        accounts.put(account.id(), account);
    }

    @Override
    public Optional<Account> lookup(Account.Id accountId) {
        return Optional.of(accounts.get(accountId));
    }

    @Override
    public void set(Account.Id accountId, Account account) {
        accounts.put(accountId, account);
    }
}
