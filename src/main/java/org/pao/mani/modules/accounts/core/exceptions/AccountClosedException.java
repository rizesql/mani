package org.pao.mani.modules.accounts.core.exceptions;

import org.pao.mani.modules.accounts.core.AccountId;

public class AccountClosedException extends RuntimeException {
    public AccountClosedException(AccountId accountId) {
        super("Account with ID " + accountId.value() + " is closed and cannot be accessed.");
    }
}
