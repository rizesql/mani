package org.pao.mani.modules.cards.web;

import org.pao.mani.modules.accounts.core.exceptions.AccountNotFoundException;
import org.pao.mani.modules.cards.core.exceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ControllerAdvice(assignableTypes = CardController.class)
public class CardControllerAdvice {
    @ExceptionHandler(SamePinException.class)
    public ResponseEntity<?> handleSamePinException(SamePinException ex) {
        var body = Map.of("error", "SAME_PIN", "message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsedPinException.class)
    public ResponseEntity<?> handleUsedPinException(UsedPinException ex) {
        var body = Map.of("error", "USED_PIN", "message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<?> handleCardNotFound(CardNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ExistingCardException.class)
    public ResponseEntity<?> handleExistingCard(ExistingCardException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
