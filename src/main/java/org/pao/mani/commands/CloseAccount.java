package org.pao.mani.commands;

import org.pao.mani.data.AccountRepository;
import org.pao.mani.domain.*;

public final class CloseAccount {
    public record Command(Account.Id id) {
    }

    private final AccountRepository accountRepository;

    public CloseAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void run(Command cmd) {
        var account = accountRepository.lookup(cmd.id).orElseThrow().activeOrThrow();
        accountRepository.set(cmd.id(), account.close());
    }
}
