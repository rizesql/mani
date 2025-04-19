package org.pao.mani.commands;

import org.pao.mani.data.CardRepository;
import org.pao.mani.domain.*;

public final class ChangeCardPin {
    public record Command(Account.Id id, Pin pin) { }

    private final CardRepository cardRepository;

    public ChangeCardPin(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void run(Command cmd) {
        var card = cardRepository.get(cmd.id()).physical().orElseThrow();
        card.changePin(cmd.pin());
        cardRepository.set(card.id(), card);
    }
}
