package org.pao.mani.core;

import org.pao.mani.core.exceptions.InvalidUnixTimestamp;

import java.time.Instant;
import java.util.Optional;

public record Timestamp(long value) implements Comparable<Timestamp> {
    public Timestamp {
        if (value < 0) {
            throw new InvalidUnixTimestamp();
        }
    }

    public Instant toInstant() {
        return Instant.ofEpochSecond(value);
    }

    public static Timestamp now() {
        return new Timestamp(Instant.now().getEpochSecond());
    }

    public static Optional<Timestamp> of(long value) {
        if (value <= 0) {
            return Optional.empty();
        }

        return Optional.of(new Timestamp(value));
    }

    @Override
    public int compareTo(Timestamp o) {
        return Long.compare(value, o.value);
    }
}
