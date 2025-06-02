package org.pao.mani.modules.accounts.web;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountRequest(
        UUID ownerId,
        BigDecimal amount,
        String currency
) {
}