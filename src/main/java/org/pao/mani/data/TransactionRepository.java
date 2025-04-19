package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> lookup(Transaction.Id id);
    List<Transaction> get(Account.Id id);
}
