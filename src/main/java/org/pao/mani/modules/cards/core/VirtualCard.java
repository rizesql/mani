package org.pao.mani.modules.cards.core;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.accounts.core.AccountId;

/**
 * Digital payment card without physical form.
 */
public final class VirtualCard extends Card {
    public VirtualCard(CardId id, AccountId accountId, Iban iban, Timestamp openedAt) {
        super(id, accountId, iban, openedAt);
    }

    public static VirtualCard of(AccountId accountId, Iban iban) {
        return new VirtualCard(CardId.generate(), accountId, iban, Timestamp.now());
    }
}
