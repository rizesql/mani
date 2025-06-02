package org.pao.mani.modules.users.core.exceptions;

import org.pao.mani.modules.users.core.Email;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(Email email) {
        super(email.value() + " is already taken");
    }
}
