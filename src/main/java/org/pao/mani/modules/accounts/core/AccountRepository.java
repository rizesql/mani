package org.pao.mani.modules.accounts.core;

import java.util.Optional;

public interface AccountRepository {
    void create(Account.Active account);
    Optional<Account> findById(AccountId accountId);
    void close(Account.Closed account);
}
