package org.pao.mani.modules.users.core.exceptions;

public class EmailLengthException extends RuntimeException {
    public EmailLengthException() {
        super("Email length must be between 5 and 255 characters");
    }
}
