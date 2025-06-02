package org.pao.mani.modules.cards.application;

import org.pao.mani.modules.accounts.core.*;
import org.pao.mani.modules.accounts.core.exceptions.*;
import org.pao.mani.modules.audit.domain.Audit;
import org.pao.mani.modules.cards.core.*;
import org.pao.mani.modules.cards.core.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Audit("cards::get")
    public AccountCards get(AccountId accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return cardRepository.get(accountId);
    }

    @Audit("cards::add")
    public CardId add(Card card) {
        var cards = cardRepository.get(card.accountId());
        var existing = card instanceof VirtualCard ? cards.virtual().isPresent() : cards.physical().isPresent();
        if (existing) {
            throw new ExistingCardException();
        }

        cardRepository.create(card);
        return card.id();
    }

    @Audit("cards::change_pin")
    public void changePin(AccountId accountId, Pin pin) {
        var card = cardRepository.get(accountId)
                .physical()
                .orElseThrow(CardNotFoundException::new);

        var existingPins = cardRepository.getPins(card.id());

        var prevPin = card.changePin(pin, existingPins);
        cardRepository.changePin(card.id(), prevPin, pin);
    }

    @Audit("cards::revoke")
    public void revoke(AccountId accountId, CardId cardId) {
        var card = cardRepository.findById(cardId)
                .orElseThrow(CardNotFoundException::new);

        if (!card.accountId().equals(accountId)) {
            throw new CardNotFoundException();
        }

        cardRepository.delete(card.id());
    }
}
