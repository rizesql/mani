package org.pao.mani.modules.accounts.infra.memory;

import org.pao.mani.modules.accounts.core.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<AccountId, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> findById(AccountId id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public void create(Account.Active account) {
        accounts.put(account.id(), account);
    }

    @Override
    public void close(Account.Closed account) {
        accounts.put(account.id(), account);
    }
}