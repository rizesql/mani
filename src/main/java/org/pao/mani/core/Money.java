package org.pao.mani.core;

import org.pao.mani.core.exceptions.NegativeAmountException;

import java.math.BigDecimal;

/**
 * Immutable monetary value (amount + currency).
 * Amounts are automatically rounded to the currency's decimal places.
 *
 * @param amount   Monetary value (non-null)
 * @param currency Currency unit (non-null)
 */
public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {
    public Money {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException();
        }
    }

    public Money convert(Currency into) {
        if (currency().equals(into)) {
            return this;
        }

        return new Money(amount().multiply(currency().exchange(into)), into);
    }

    public Money subtract(Money other) {
        return new Money(amount().subtract(other.amount()), currency());
    }

    public Money add(Money other) {
        return new Money(amount().add(other.amount()), currency());
    }

    @Override
    public int compareTo(Money o) {
        return amount.compareTo(o.convert(currency()).amount());
    }
}
