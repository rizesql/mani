package org.pao.mani.modules.accounts.core;

import org.pao.mani.core.Money;
import org.pao.mani.modules.transactions.core.Transaction;

import java.util.List;

/**
 * Account current (accountId + current amount).
 *
 * @param accountId Associated account identifier
 * @param current   Current money amount
 */
public record Balance(AccountId accountId, Money current) {
    public static Balance from(Account.Active account, List<Transaction> transactions) {
        var balance = account.credit();
        for (var t : transactions) {
            var amount = t.amount().convert(account.credit().currency());

            if (account.id().equals(t.creditAccountId())) {
                balance = balance.add(amount);
            } else {
                balance = balance.subtract(amount);
            }
        }

        return new Balance(account.id(), balance);
    }
}
