package org.pao.mani.modules.cards.core;

import org.pao.mani.modules.accounts.core.AccountId;

import java.util.Optional;
import java.util.Set;

public interface CardRepository {
    AccountCards get(AccountId id);
    Optional<? extends Card> findById(CardId id);
    void create(Card card);
    void delete(CardId cardId);
    Set<Pin> getPins(CardId id);
    void changePin(CardId cardId, Pin previousPin, Pin newPin);
}
