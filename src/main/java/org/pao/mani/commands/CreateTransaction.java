package org.pao.mani.commands;

import org.pao.mani.data.AccountRepository;
import org.pao.mani.data.TransactionRepository;
import org.pao.mani.domain.*;


public final class CreateTransaction {
    public record Command(Account.Id creditAccountId, Account.Id debitAccountId, Money amount) {
    }

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public CreateTransaction(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction.Id run(Command cmd) {
        var creditor = accountRepository.lookup(cmd.creditAccountId()).orElseThrow().activeOrThrow();
        var debtor = accountRepository.lookup(cmd.debitAccountId()).orElseThrow().activeOrThrow();

        var balance = Balance.from(
                creditor,
                transactionRepository.get(cmd.creditAccountId())
        );

        var transaction = creditor.transfer(debtor, cmd.amount(), balance);
        transactionRepository.save(transaction);

        return transaction.id();
    }
}
