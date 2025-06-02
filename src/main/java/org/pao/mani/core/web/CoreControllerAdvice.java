package org.pao.mani.core.web;

import org.pao.mani.core.exceptions.InvalidUnixTimestamp;
import org.pao.mani.core.exceptions.NegativeAmountException;
import org.pao.mani.core.exceptions.UnknownCurrencyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CoreControllerAdvice {
    @ExceptionHandler(UnknownCurrencyException.class)
    public ResponseEntity<?> handleUnknownCurrency(UnknownCurrencyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<?> handleNegativeAmount(NegativeAmountException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUnixTimestamp.class)
    public ResponseEntity<?> handleInvalidUnixTimestamp(InvalidUnixTimestamp ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
