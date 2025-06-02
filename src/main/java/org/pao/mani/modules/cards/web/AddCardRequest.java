package org.pao.mani.modules.cards.web;

import java.util.Optional;

public record AddCardRequest(
        String iban,
        Optional<String> pin
) {
}