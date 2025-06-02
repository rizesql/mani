package org.pao.mani.modules.users.core;

import org.pao.mani.modules.users.core.exceptions.*;

import java.util.Objects;

public record Username(String value) {
    public Username {
        Objects.requireNonNull(value, "Username cannot be null");

        if (value.length() < 3 || value.length() > 32) {
            throw new UsernameLengthException();
        }

        if (!value.matches("^[a-zA-Z0-9_]{3,32}$")) {
            throw new UsernameCharactersException();
        }
    }
}
