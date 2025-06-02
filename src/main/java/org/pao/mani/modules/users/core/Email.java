package org.pao.mani.modules.users.core;

import org.pao.mani.modules.users.core.exceptions.*;

import java.util.Objects;

public record Email(String value) {
    public Email {
        Objects.requireNonNull(value, "Email cannot be null");

        if (value.length() < 5 || value.length() > 255) {
            throw new EmailLengthException();
        }

        if (!value.matches("^.+@.+\\..+$")) {
            throw new InvalidEmailException(value);
        }
    }
}
