package org.pao.mani.modules.cards.core;

import java.util.Optional;

public record AccountCards(
        Optional<PhysicalCard> physical,
        Optional<VirtualCard> virtual
) {
}
