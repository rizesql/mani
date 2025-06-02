package org.pao.mani.modules.accounts.web;

import org.pao.mani.modules.accounts.core.Account;

import java.math.BigDecimal;
import java.util.UUID;

public sealed interface LookupAccountResponse {
    static LookupAccountResponse from(Account account) {
        return switch (account) {
            case Account.Active active -> Active.from(active);
            case Account.Closed closed -> Closed.from(closed);
        };
    }

    record Active(UUID accountId, UUID ownerId, BigDecimal amount, String currency) implements LookupAccountResponse {
        public static Active from(Account.Active account) {
            return new Active(
                    account.id().value(),
                    account.ownerId().value(),
                    account.credit().amount(),
                    account.credit().currency().into()
            );
        }
    }

    record Closed(UUID accountId, long closedAt) implements LookupAccountResponse {
        public static Closed from(Account.Closed account) {
            return new Closed(account.id().value(), account.closedAt().value());
        }
    }
}
