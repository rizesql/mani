package org.pao.mani.modules.cards.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a 4-digit Personal Identification Number (PIN) with secure storage and comparison.
 * The PIN is validated for length and numeric digits only, and uses constant-time comparison
 * to prevent timing attacks.
 */
public final class Pin {
    private static final int LENGTH = 4;
    private static final Pattern PATTERN = Pattern.compile("^\\d{%d}$".formatted(LENGTH));

    private final String value;

    public Pin(String pin) {
        Objects.requireNonNull(pin, "pin cannot be null");
        if (!PATTERN.matcher(pin).matches()) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits (0-9)");
        }

        this.value = pin;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "[PIN]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var other = (Pin) o;
        return MessageDigest.isEqual(
                this.value.getBytes(StandardCharsets.UTF_8),
                other.value.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
