package org.pao.mani.domain;

import java.util.Objects;
import java.util.UUID;

public sealed interface Account {
    record Id(UUID id) {
        public Id {
            Objects.requireNonNull(id, "Id cannot be null");
        }

        public static Account.Id generate() {
            return new Account.Id(UUID.randomUUID());
        }
    }

    record Closed(Id id) implements Account {
    }

    record Active(Id id, User.Id ownerId, Money initialBalance) implements Account {
        public Closed close() {
            return new Closed(id);
        }

        public Transaction transfer(Active to, Money amount, Balance balance) {
            if (balance.balance().convert(initialBalance().currency()).compareTo(amount.convert(initialBalance().currency())) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }

            return new Transaction(Transaction.Id.generate(), id(), to.id(), amount, Timestamp.now());
        }
    }

    static Active open(User.Id ownerId, Money initialBalance) {
        return new Active(Id.generate(), ownerId, initialBalance);
    }

    default Active activeOrThrow() {
        return switch (this) {
            case Active o -> o;
            case Closed c -> throw new IllegalStateException("Closed");
        };
    }
}
