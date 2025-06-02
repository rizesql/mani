package org.pao.mani.modules.users.core;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Objects.requireNonNull(value, "Id cannot be null");
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }
}