package org.pao.mani.modules.cards.core.exceptions;

public class UsedPinException extends RuntimeException {
    public UsedPinException() {
        super("Pin was previously used");
    }
}
