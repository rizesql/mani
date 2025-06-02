package org.pao.mani.modules.users.core.exceptions;

public class SameEmailException extends RuntimeException {
    public SameEmailException() {
        super("Email is the same");
    }
}
