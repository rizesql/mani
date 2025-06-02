package org.pao.mani.modules.cards.core;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.cards.core.exceptions.*;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Set;

/**
 * Physical payment card with PIN protection.
 */
public final class PhysicalCard extends Card {
    private Pin pin;

    public PhysicalCard(CardId id, AccountId accountId, Iban iban, Pin pin, Timestamp openedAt) {
        super(id, accountId, iban, openedAt);
        this.pin = pin;
    }

    public static PhysicalCard of(AccountId accountId, Iban iban, Pin pin) {
        return new PhysicalCard(CardId.generate(), accountId, iban, pin, Timestamp.now());
    }

    public YearMonth expires() {
        return YearMonth.from(openedAt().toInstant().plus(4, ChronoUnit.YEARS).atZone(ZoneOffset.UTC));
    }

    public Pin pin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    public Pin changePin(Pin newPin, Set<Pin> previousPins) {
        Objects.requireNonNull(newPin, "newPin cannot be null");

        if (this.pin.equals(newPin)) {
            throw new SamePinException();
        }

        if (previousPins.contains(newPin)) {
            throw new UsedPinException();
        }

        var previousPin = this.pin;
        this.pin = newPin;
        return previousPin;
    }
}