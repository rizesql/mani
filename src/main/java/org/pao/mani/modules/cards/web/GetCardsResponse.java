package org.pao.mani.modules.cards.web;

import org.pao.mani.modules.cards.core.AccountCards;
import org.pao.mani.modules.cards.core.Card;

import java.util.Optional;
import java.util.UUID;

public record GetCardsResponse(Optional<CardResponse> physical, Optional<CardResponse> virtual) {
    private record CardResponse(UUID id, UUID accountId, String iban, String openedAt) {
        public static CardResponse from(Card card) {
            return new CardResponse(
                    card.id().value(),
                    card.accountId().value(),
                    card.iban().value(),
                    card.openedAt().toInstant().toString()
            );
        }
    }

    public static GetCardsResponse from(AccountCards cards) {
        return new GetCardsResponse(
                cards.physical().map(CardResponse::from),
                cards.virtual().map(CardResponse::from)
        );
    }
}
