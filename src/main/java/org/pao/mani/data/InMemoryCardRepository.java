package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryCardRepository implements CardRepository {
    private record AccountCardsIds(Optional<Card.Id> physical, Optional<Card.Id> virtual) {
        public AccountCardsIds withPhysical(Card.Id physical) {
            return new AccountCardsIds(Optional.of(physical), virtual);
        }

        public AccountCardsIds withVirtual(Card.Id virtual) {
            return new AccountCardsIds(physical, Optional.of(virtual));
        }

        public AccountCardsIds revokePhysical() {
            return new AccountCardsIds(Optional.empty(), virtual);
        }

        public AccountCardsIds revokeVirtual() {
            return new AccountCardsIds(physical, Optional.empty());
        }
    }

    private final HashMap<Card.Id, Card.Virtual> virtualCards = new HashMap<>();
    private final HashMap<Card.Id, Card.Physical> physicalCards = new HashMap<>();
    private final HashMap<Account.Id, AccountCardsIds> accountCards = new HashMap<>();

    @Override
    public AccountCards get(Account.Id id) {
        var cards = accountCards.get(id);
        if (cards == null) {
            return new AccountCards(Optional.empty(), Optional.empty());
        }

        return new AccountCards(
                cards.physical().map(physicalCards::get),
                cards.virtual().map(virtualCards::get)
        );
    }

    @Override
    public Optional<? extends Card> lookup(Card.Id id) {
        var virtual = Optional.ofNullable(virtualCards.get(id));
        var physical = Optional.ofNullable(physicalCards.get(id));
        if (virtual.isPresent() && physical.isPresent()) {
            throw new IllegalStateException("Duplicate cards");
        }

        return virtual.isEmpty() ? physical : virtual;
    }

    @Override
    public void save(Card card) {
        accountCards.putIfAbsent(card.accountId(), new AccountCardsIds(Optional.empty(), Optional.empty()));
        switch (card) {
            case Card.Physical p -> {
                physicalCards.put(card.id(), p);
                accountCards.computeIfPresent(
                        card.accountId(),
                        (k, cards) -> cards.withPhysical(p.id()));
            }
            case Card.Virtual v -> {
                virtualCards.put(card.id(), v);
                accountCards.computeIfPresent(
                        card.accountId(),
                        (k, cards) -> cards.withVirtual(v.id()));
            }
        }
    }

    @Override
    public void set(Card.Id id, Card card) {
        switch (card) {
            case Card.Physical p -> {
                physicalCards.put(card.id(), p);
                accountCards.compute(
                        card.accountId(),
                        (k, cards) -> Objects.requireNonNull(cards).withPhysical(p.id()));
            }
            case Card.Virtual v -> {
                virtualCards.put(card.id(), v);
                accountCards.compute(
                        card.accountId(),
                        (k, cards) -> Objects.requireNonNull(cards).withPhysical(v.id()));
            }
        }
    }

    @Override
    public void revoke(Card card) {
        switch (card) {
            case Card.Physical p -> {
                physicalCards.put(card.id(), p);
                accountCards.computeIfPresent(
                        card.accountId(),
                        (k, cards) -> cards.revokePhysical());
            }
            case Card.Virtual v -> {
                virtualCards.put(card.id(), v);
                accountCards.computeIfPresent(
                        card.accountId(),
                        (k, cards) -> cards.revokeVirtual());
            }
        }
    }
}
