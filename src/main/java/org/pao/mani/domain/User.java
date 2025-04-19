package org.pao.mani.domain;

import java.util.Objects;
import java.util.UUID;

public final class User {
    public record Id(UUID id) {
        public Id {
            Objects.requireNonNull(id, "Id cannot be null");
        }

        public static Id generate() {
            return new Id(UUID.randomUUID());
        }
    }

    private final Id id;
    private Username username;

    public User(Id id, Username username) {
        this.id = id;
        this.username = username;
    }

    public Id getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }
}
