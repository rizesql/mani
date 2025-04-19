package org.pao.mani.commands;

import org.pao.mani.data.CardRepository;
import org.pao.mani.domain.Account;
import org.pao.mani.domain.Card;

public final class AddCard {
    public record Command(Account.Id id, Card card) {}

    private final CardRepository cardRepository;
    public AddCard(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card.Id run(Command cmd) {
        cardRepository.save(cmd.card());
        return cmd.card.id();
    }
}
