package org.pao.mani.domain;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public abstract sealed class Card {
    public record Id(UUID id) {
        public Id {
            Objects.requireNonNull(id, "Id cannot be null");
        }

        public static Id generate() {
            return new Id(UUID.randomUUID());
        }
    }

    private final Id id;
    private final Account.Id accountId;
    private final Iban iban;
    private final Timestamp openedAt;

    protected Card(Id id, Account.Id accountId, Iban iban, Timestamp openedAt) {
        this.id = id;
        this.accountId = accountId;
        this.iban = iban;
        this.openedAt = openedAt;
    }

    public Id id() {
        return id;
    }

    public Account.Id accountId() {
        return accountId;
    }

    public Iban iban() {
        return iban;
    }

    public Timestamp openedAt() {
        return openedAt;
    }

    public static final class Virtual extends Card {
        public Virtual(Id id, Account.Id accountId, Iban iban, Timestamp openedAt) {
            super(id, accountId, iban, openedAt);
        }

        public static Virtual of(Account.Id accountId, Iban iban) {
            return new Virtual(Id.generate(), accountId, iban, Timestamp.now());
        }
    }

    public static final class Physical extends Card {
        private Pin pin;

        private final Set<Pin> previousPins = new HashSet<>();

        public Physical(Id id, Account.Id accountId, Iban iban, Pin pin, Timestamp openedAt) {
            super(id, accountId, iban, openedAt);
            this.pin = pin;
        }

        public static Physical of(Account.Id accountId, Iban iban, Pin pin) {
            return new Physical(Id.generate(), accountId, iban, pin, Timestamp.now());
        }

        public YearMonth expires() {
            return YearMonth.from(openedAt().toInstant().plus(4, ChronoUnit.YEARS).atZone(ZoneOffset.UTC));
        }

        public Pin pin() {
            return pin;
        }

        public void changePin(Pin pin) {
            Objects.requireNonNull(pin, "pin cannot be null");

            if (previousPins.contains(pin)) {
                throw new IllegalArgumentException("Cannot reuse the same pin");
            }

            previousPins.add(pin);
            this.pin = pin;
        }
    }
}
