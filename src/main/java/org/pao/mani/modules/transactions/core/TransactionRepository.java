package org.pao.mani.modules.transactions.core;

import org.pao.mani.modules.accounts.core.AccountId;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    void create(Transaction transaction);

    Optional<Transaction> findById(TransactionId id);

    List<Transaction> get(AccountId id);
}
