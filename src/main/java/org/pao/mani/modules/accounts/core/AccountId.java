package org.pao.mani.modules.accounts.core;


import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for an Account.
 */
public record AccountId(UUID value) {
    public AccountId {
        Objects.requireNonNull(value);
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }
}