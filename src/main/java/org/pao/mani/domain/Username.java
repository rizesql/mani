package org.pao.mani.domain;

import java.util.Objects;

public record Username(String value) {
    public Username {
        Objects.requireNonNull(value, "Username cannot be null");

        if(value.length() < 3 || value.length() > 32) {
          throw new IllegalArgumentException("Username length must be between 3 and 32 characters");
        }

        if(!value.matches("^[a-zA-Z0-9_]{3,32}$")) {
            throw new IllegalArgumentException("Username can only contain alphanumeric characters and underscores");
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }
}
