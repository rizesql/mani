package org.pao.mani.modules.users.core.exceptions;

public class UsernameLengthException extends RuntimeException {
    public UsernameLengthException() {
        super("Username length must be between 3 and 32 characters");
    }
}
