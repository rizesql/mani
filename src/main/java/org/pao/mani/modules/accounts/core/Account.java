package org.pao.mani.modules.accounts.core;

import org.pao.mani.core.*;
import org.pao.mani.modules.accounts.core.exceptions.*;
import org.pao.mani.modules.transactions.core.*;
import org.pao.mani.modules.users.core.UserId;

import java.util.Optional;

/**
 * A sealed interface representing the state of a bank account.
 * An account can be either {@link Account.Active} or {@link Account.Closed}.
 */
public sealed interface Account {
    /**
     * Represents a closed bank account.
     *
     * @param id       the unique identifier of the account
     * @param closedAt the timestamp indicating when the account was closed
     */
    record Closed(AccountId id, Timestamp closedAt) implements Account {
    }

    /**
     * Represents an active bank account that can participate in transactions.
     *
     * @param id      the unique identifier of the account
     * @param ownerId the ID of the user who owns the account
     * @param credit  the initial credit inserted into the account upon creation
     */
    record Active(AccountId id, UserId ownerId, Money credit) implements Account {
        public Closed close() {
            return new Closed(id, Timestamp.now());
        }

        public Transaction transfer(Active to, Money amount, Balance balance) {
            if (balance.current().convert(credit().currency()).compareTo(amount.convert(credit().currency())) < 0) {
                throw new InsufficientFundsException();
            }

            return new Transaction(TransactionId.generate(), id(), to.id(), amount, Timestamp.now());
        }
    }

    static Active open(UserId ownerId, Money credit) {
        return new Active(AccountId.generate(), ownerId, credit);
    }

    default Optional<Active> active() {
        return switch (this) {
            case Active a -> Optional.of(a);
            case Closed c -> Optional.empty();
        };
    }

    default Active activeOrThrow() {
        return switch (this) {
            case Active o -> o;
            case Closed c -> throw new AccountClosedException(c.id());
        };
    }
}
