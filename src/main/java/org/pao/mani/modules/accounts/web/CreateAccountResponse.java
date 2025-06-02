package org.pao.mani.modules.accounts.web;

import org.pao.mani.modules.accounts.core.AccountId;

import java.util.UUID;

public record CreateAccountResponse(UUID accountId) {
    public static CreateAccountResponse from(AccountId accountId) {
        return new CreateAccountResponse(accountId.value());
    }
}
