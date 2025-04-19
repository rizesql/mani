package org.pao.mani.domain;

import java.util.Optional;

public record AccountCards(Optional<Card.Physical> physical, Optional<Card.Virtual> virtual) {
}
