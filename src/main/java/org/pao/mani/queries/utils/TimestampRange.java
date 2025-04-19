package org.pao.mani.queries.utils;

import org.pao.mani.domain.Timestamp;

import java.util.Optional;

public record TimestampRange(Optional<Timestamp> from, Optional<Timestamp> to) {
    public TimestampRange {
        if (from.isPresent() && to.isPresent() && from.get().after(to.get())) {
            throw new IllegalArgumentException("Invalid timestamp range");
        }
    }

    public static TimestampRange empty() {
        return new TimestampRange(Optional.empty(), Optional.empty());
    }

    public boolean includes(Timestamp timestamp) {
        if (from.isPresent() && timestamp.compareTo(from.get()) < 0) {
            return false;
        }

        return to.isEmpty() || timestamp.compareTo(to.get()) < 0;
    }
}
