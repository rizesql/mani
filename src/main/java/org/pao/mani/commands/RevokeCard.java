package org.pao.mani.commands;

import org.pao.mani.data.CardRepository;
import org.pao.mani.domain.Card;

public final class RevokeCard {
    public record Command(Card.Id id) { }

    private final CardRepository cardRepository;
    public RevokeCard(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void run(Command cmd) {
        var card = cardRepository.lookup(cmd.id()).orElseThrow();
        cardRepository.revoke(card);
    }
}
