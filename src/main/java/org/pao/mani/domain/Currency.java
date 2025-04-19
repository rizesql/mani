package org.pao.mani.domain;

import java.math.BigDecimal;

public sealed interface Currency {
    BigDecimal exchange(Currency into);

    Eur EUR = new Eur();
    Ron RON = new Ron();

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
