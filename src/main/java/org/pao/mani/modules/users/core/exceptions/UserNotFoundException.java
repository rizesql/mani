package org.pao.mani.modules.users.core.exceptions;

import org.pao.mani.modules.users.core.UserId;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UserId userId) {
        super("User with id " + userId + " not found");
    }
}
