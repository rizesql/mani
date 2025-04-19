package org.pao.mani.domain;

import java.math.BigDecimal;

public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {
    public Money {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static Money eur(long amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.EUR);
    }

    public static Money ron(long amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.RON);
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
