package org.pao.mani.domain;

import java.util.List;

public record Balance(Account.Id accountId, Money balance) {
    public static Balance from(Account.Active account, List<Transaction> transactions) {
        var balance = account.initialBalance();
        for(var t : transactions) {
            var amount = t.amount().convert(account.initialBalance().currency());

            if(account.id().equals(t.creditAccountId())) {
                balance = balance.add(amount);
            } else {
                balance = balance.subtract(amount);
            }
        }

        return new Balance(account.id(), balance);
    }
}
