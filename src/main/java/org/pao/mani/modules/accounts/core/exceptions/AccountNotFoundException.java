package org.pao.mani.modules.accounts.core.exceptions;

import org.pao.mani.modules.accounts.core.AccountId;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(AccountId accountId) {
        super("Account not found: " + accountId.value());
    }
}
