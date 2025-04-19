package org.pao.mani.domain;

import java.time.Instant;

public record Timestamp(long value) implements Comparable<Timestamp> {
    public Timestamp {
        if(value < 0) {
            throw new IllegalArgumentException("Invalid Unix timestamp");
        }
    }

    public Instant toInstant() {
        return Instant.ofEpochSecond(value);
    }

    public static Timestamp now() {
        return new Timestamp(Instant.now().getEpochSecond());
    }

    @Override
    public int compareTo(Timestamp o) {
        return Long.compare(value, o.value);
    }

    public boolean after(Timestamp o) {
        return value > o.value;
    }

    public boolean before(Timestamp o) {
        return value < o.value;
    }
}
