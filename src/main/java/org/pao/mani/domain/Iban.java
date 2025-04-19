package org.pao.mani.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public record Iban(String value) {
    private static final Pattern IBAN_PATTERN = Pattern.compile(
            "^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$"
    );

    public Iban {
        Objects.requireNonNull(value, "Iban cannot be null");
        if (!IBAN_PATTERN.matcher(value.replaceAll("\\s+", "").toUpperCase()).matches()) {
            throw new IllegalArgumentException("Invalid IBAN: " + value);
        }
    }

    public String countryCode() {
        return value.substring(0, 2);
    }

    public String formatted() {
        return value.replaceAll("(.{4})", "$1 ").trim();
    }
}
