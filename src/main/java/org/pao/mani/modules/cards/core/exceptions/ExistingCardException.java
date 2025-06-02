package org.pao.mani.modules.cards.core.exceptions;

public class ExistingCardException extends RuntimeException {
    public ExistingCardException() {
        super("Card already exists");
    }
}
