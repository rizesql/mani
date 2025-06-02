package org.pao.mani.modules.transactions.application;

import org.pao.mani.core.Money;
import org.pao.mani.modules.accounts.core.*;
import org.pao.mani.modules.accounts.core.exceptions.AccountNotFoundException;
import org.pao.mani.modules.audit.domain.Audit;
import org.pao.mani.modules.transactions.core.*;
import org.pao.mani.modules.transactions.core.exceptions.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Audit("transactions::list")
    public List<Transaction> get(AccountId accountId) {
        return transactionRepository.get(accountId);
    }

    @Audit("transactions::get")
    public Transaction get(TransactionId transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }

    @Audit("transactions::create")
    public TransactionId create(AccountId creditAccountId, AccountId debitAccountId, Money amount) {
        var creditor = accountRepository.findById(creditAccountId)
                .orElseThrow(() -> new AccountNotFoundException(creditAccountId))
                .activeOrThrow();
        var debtor = accountRepository.findById(debitAccountId)
                .orElseThrow(() -> new AccountNotFoundException(debitAccountId))
                .activeOrThrow();

        var balance = Balance.from(
                creditor,
                transactionRepository.get(creditAccountId)
        );

        var transaction = creditor.transfer(debtor, amount, balance);
        transactionRepository.create(transaction);

        return transaction.id();
    }
}
