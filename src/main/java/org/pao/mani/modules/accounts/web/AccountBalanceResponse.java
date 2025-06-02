package org.pao.mani.modules.accounts.web;

import org.pao.mani.modules.accounts.core.Balance;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountBalanceResponse(UUID accountId, BigDecimal amount, String currency) {
    public static AccountBalanceResponse from(Balance balance) {
        return new AccountBalanceResponse(
                balance.accountId().value(),
                balance.current().amount(),
                balance.current().currency().into()
        );
    }
}
