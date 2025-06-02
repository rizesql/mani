package org.pao.mani.modules.users.core.exceptions;

public class SameUsernameException extends RuntimeException {
    public SameUsernameException() {
        super("Username is the same");
    }
}
