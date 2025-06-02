package org.pao.mani.modules.users.core.exceptions;

public class UsernameCharactersException extends RuntimeException {
    public UsernameCharactersException() {
        super("Username can only contain alphanumeric characters and underscores");
    }
}
