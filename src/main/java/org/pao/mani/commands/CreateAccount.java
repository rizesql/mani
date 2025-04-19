package org.pao.mani.commands;

import org.pao.mani.data.AccountRepository;
import org.pao.mani.domain.*;

public final class CreateAccount {
    public record Command(User.Id ownerId, Money credit) {}

    private final AccountRepository repository;

    public CreateAccount(AccountRepository repository) {
        this.repository = repository;
    }

    public Account.Id run(Command cmd) {
        var account = Account.open(cmd.ownerId(), cmd.credit);
        repository.save(account);
        return account.id();
    }
}
