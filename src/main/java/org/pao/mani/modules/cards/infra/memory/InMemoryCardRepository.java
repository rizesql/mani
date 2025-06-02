package org.pao.mani.modules.cards.infra.memory;

import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.cards.core.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCardRepository implements CardRepository {
    private final Map<CardId, Card> cards = new ConcurrentHashMap<>();
    private final Map<AccountId, AccountCards> accountCards = new ConcurrentHashMap<>();
    private final Map<CardId, Set<Pin>> previousPins = new ConcurrentHashMap<>();

    @Override
    public AccountCards get(AccountId id) {
        return accountCards.getOrDefault(
                id,
                new AccountCards(Optional.empty(), Optional.empty())
        );
    }

    @Override
    public Optional<? extends Card> findById(CardId id) {
        return Optional.ofNullable(cards.get(id));
    }

    @Override
    public void create(Card card) {
        cards.put(card.id(), card);

        accountCards.compute(card.accountId(), (accountId, existing) -> {
            if (existing == null) {
                return new AccountCards(card.physical(), card.virtual());
            }

            return new AccountCards(
                    card.physical().isPresent() ? card.physical() : existing.physical(),
                    card.virtual().isPresent() ? card.virtual() : existing.virtual()
            );
        });

        if (card instanceof PhysicalCard pc) {
            previousPins.put(pc.id(), new HashSet<>(Set.of(pc.pin())));
        }
    }

    @Override
    public void delete(CardId cardId) {
        var removed = cards.remove(cardId);
        if (removed == null) {
            return;
        }

        accountCards.computeIfPresent(removed.accountId(), (id, existing) ->
                new AccountCards(
                        existing.physical().filter(c -> !c.id().equals(cardId)),
                        existing.virtual().filter(c -> !c.id().equals(cardId))
                )
        );

        previousPins.remove(cardId);
    }

    @Override
    public Set<Pin> getPins(CardId id) {
        return Set.copyOf(previousPins.getOrDefault(id, Set.of()));
    }

    @Override
    public void changePin(CardId cardId, Pin previousPin, Pin newPin) {
        cards.computeIfPresent(cardId, (id, card) -> {
            card.physical().orElseThrow().setPin(newPin);
            return card;
        });
        previousPins.computeIfAbsent(cardId, __ -> new HashSet<>()).add(previousPin);
    }

//    private record AccountCardsIds(Optional<CardId> physical, Optional<CardId> virtual) {
//        public AccountCardsIds withPhysical(CardId physical) {
//            return new AccountCardsIds(Optional.of(physical), virtual);
//        }
//
//        public AccountCardsIds withVirtual(CardId virtual) {
//            return new AccountCardsIds(physical, Optional.of(virtual));
//        }
//
//        public AccountCardsIds revokePhysical() {
//            return new AccountCardsIds(Optional.empty(), virtual);
//        }
//
//        public AccountCardsIds revokeVirtual() {
//            return new AccountCardsIds(physical, Optional.empty());
//        }
//    }
//
//    private final HashMap<CardId, VirtualCard> virtualCards = new HashMap<>();
//    private final HashMap<CardId, PhysicalCard> physicalCards = new HashMap<>();
//    private final HashMap<AccountId, AccountCardsIds> accountCards = new HashMap<>();
//
//    @Override
//    public AccountCards get(AccountId value) {
//        var cards = accountCards.get(value);
//        if (cards == null) {
//            return new AccountCards(Optional.empty(), Optional.empty());
//        }
//
//        return new AccountCards(
//                cards.physical().map(physicalCards::get),
//                cards.virtual().map(virtualCards::get)
//        );
//    }
//
//    @Override
//    public Optional<? extends Card> findById(CardId value) {
//        var virtual = Optional.ofNullable(virtualCards.get(value));
//        var physical = Optional.ofNullable(physicalCards.get(value));
//        if (virtual.isPresent() && physical.isPresent()) {
//            throw new IllegalStateException("Duplicate cards");
//        }
//
//        return virtual.isEmpty() ? physical : virtual;
//    }
//
//    @Override
//    public Set<Pin> getPins(CardId value) {
//        return Set.of();
//    }
//
//    @Override
//    public void create(Card card) {
//        accountCards.putIfAbsent(card.accountId(), new AccountCardsIds(Optional.empty(), Optional.empty()));
//        switch (card) {
//            case PhysicalCard p -> {
//                physicalCards.put(card.value(), p);
//                accountCards.computeIfPresent(
//                        card.accountId(),
//                        (k, cards) -> cards.withPhysical(p.value()));
//            }
//            case VirtualCard v -> {
//                virtualCards.put(card.value(), v);
//                accountCards.computeIfPresent(
//                        card.accountId(),
//                        (k, cards) -> cards.withVirtual(v.value()));
//            }
//        }
//    }
//
//    @Override
//    public void set(CardId value, Card card) {
//        switch (card) {
//            case PhysicalCard p -> {
//                physicalCards.put(card.value(), p);
//                accountCards.compute(
//                        card.accountId(),
//                        (k, cards) -> Objects.requireNonNull(cards).withPhysical(p.value()));
//            }
//            case VirtualCard v -> {
//                virtualCards.put(card.value(), v);
//                accountCards.compute(
//                        card.accountId(),
//                        (k, cards) -> Objects.requireNonNull(cards).withPhysical(v.value()));
//            }
//        }
//    }
//
//    @Override
//    public void delete(Card card) {
//        switch (card) {
//            case PhysicalCard p -> {
//                physicalCards.put(card.value(), p);
//                accountCards.computeIfPresent(
//                        card.accountId(),
//                        (k, cards) -> cards.revokePhysical());
//            }
//            case VirtualCard v -> {
//                virtualCards.put(card.value(), v);
//                accountCards.computeIfPresent(
//                        card.accountId(),
//                        (k, cards) -> cards.revokeVirtual());
//            }
//        }
//    }
}
