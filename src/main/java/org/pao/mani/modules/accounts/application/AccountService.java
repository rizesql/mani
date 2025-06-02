package org.pao.mani.modules.accounts.application;

import org.pao.mani.core.*;
import org.pao.mani.modules.accounts.core.*;
import org.pao.mani.modules.accounts.core.exceptions.AccountNotFoundException;
import org.pao.mani.modules.audit.domain.Audit;
import org.pao.mani.modules.transactions.core.TransactionRepository;
import org.pao.mani.modules.users.core.UserId;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Audit("accounts::lookup")
    public Account lookup(AccountId accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Audit("accounts::balance")
    public Balance balance(AccountId accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId))
                .activeOrThrow();
        var transactions = transactionRepository.get(accountId);

        return Balance.from(account, transactions);
    }

    @Audit("accounts::create")
    public AccountId create(UserId ownerId, Money credit) {
        var account = Account.open(ownerId, credit);
        accountRepository.create(account);
        return account.id();
    }

    @Audit("accounts::close")
    public void close(AccountId accountId) {
        var account = accountRepository
                .findById(accountId)
                .flatMap(Account::active)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        accountRepository.close(account.close());
    }
}
