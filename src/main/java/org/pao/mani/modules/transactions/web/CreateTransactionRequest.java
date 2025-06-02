package org.pao.mani.modules.transactions.web;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(
        UUID creditAccountId,
        UUID debitAccountId,
        BigDecimal amount,
        String currency
) {
}