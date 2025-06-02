package org.pao.mani.modules.cards.core;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a Card.
 */
public record CardId(UUID value) {
    public CardId {
        Objects.requireNonNull(value, "Id cannot be null");
    }

    public static CardId generate() {
        return new CardId(UUID.randomUUID());
    }
}
