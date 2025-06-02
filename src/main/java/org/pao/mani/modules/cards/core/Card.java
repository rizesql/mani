package org.pao.mani.modules.cards.core;

import org.pao.mani.modules.accounts.core.*;
import org.pao.mani.core.*;

import java.util.Optional;

/**
 * Payment card (either {@link PhysicalCard} or {@link VirtualCard}).
 */
public abstract sealed class Card permits PhysicalCard, VirtualCard {
    private final CardId id;
    private final AccountId accountId;
    private final Iban iban;
    private final Timestamp openedAt;

    protected Card(CardId id, AccountId accountId, Iban iban, Timestamp openedAt) {
        this.id = id;
        this.accountId = accountId;
        this.iban = iban;
        this.openedAt = openedAt;
    }

    public static Card of(AccountId accountId, Iban iban, Optional<Pin> pin) {
        if (pin.isPresent()) {
            return PhysicalCard.of(accountId, iban, pin.get());
        }

        return VirtualCard.of(accountId, iban);
    }

    public Optional<PhysicalCard> physical() {
        return this instanceof PhysicalCard pc ? Optional.of(pc) : Optional.empty();
    }

    public Optional<VirtualCard> virtual() {
        return this instanceof VirtualCard pc ? Optional.of(pc) : Optional.empty();
    }

    public CardId id() {
        return id;
    }

    public AccountId accountId() {
        return accountId;
    }

    public Iban iban() {
        return iban;
    }

    public Timestamp openedAt() {
        return openedAt;
    }
}
