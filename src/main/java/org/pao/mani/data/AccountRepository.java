package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.Optional;

public interface AccountRepository {
    void save(Account.Active account);
    Optional<Account> lookup(Account.Id accountId);
    void set(Account.Id accountId, Account account);
}
