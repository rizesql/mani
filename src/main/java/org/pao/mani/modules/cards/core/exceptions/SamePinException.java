package org.pao.mani.modules.cards.core.exceptions;

public class SamePinException extends RuntimeException {
    public SamePinException() {
        super("Cannot set the same pin");
    }
}
