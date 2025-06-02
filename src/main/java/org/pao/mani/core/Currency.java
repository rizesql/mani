package org.pao.mani.core;

import org.pao.mani.core.exceptions.UnknownCurrencyException;

import java.math.BigDecimal;

public sealed interface Currency {
    BigDecimal exchange(Currency into);

    Eur EUR = new Eur();
    Ron RON = new Ron();

    static Currency from(String code) {
        return switch (code.toUpperCase()) {
            case "EUR" -> EUR;
            case "RON" -> RON;
            default -> throw new UnknownCurrencyException(code);
        };
    }

    default String into() {
        return switch (this) {
            case Eur eur -> "EUR";
            case Ron ron -> "RON";
        };
    }

    record Eur() implements Currency {
        @Override
        public BigDecimal exchange(Currency into) {
            return BigDecimal.valueOf(switch (into) {
                case Eur eur -> 1;
                case Ron ron -> 5;
            });
        }
    }

    record Ron() implements Currency {
        @Override
        public BigDecimal exchange(Currency into) {
            return BigDecimal.valueOf(switch (into) {
                case Eur eur -> 0.2;
                case Ron ron -> 1;
            });
        }
    }
}
