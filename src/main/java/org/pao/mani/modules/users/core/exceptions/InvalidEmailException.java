package org.pao.mani.modules.users.core.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String value) {
        super(value + " is not a valid email address");
    }
}
