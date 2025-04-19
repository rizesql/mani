package org.pao.mani.data;

import org.pao.mani.domain.*;

import java.util.Optional;

public interface CardRepository {
    AccountCards get(Account.Id id);
    Optional<? extends Card> lookup(Card.Id id);
    void save(Card card);
    void set(Card.Id id, Card card);
    void revoke(Card card);
}
