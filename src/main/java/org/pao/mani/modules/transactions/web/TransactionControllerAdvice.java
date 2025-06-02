package org.pao.mani.modules.transactions.web;

import org.pao.mani.modules.accounts.core.exceptions.*;
import org.pao.mani.modules.transactions.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ControllerAdvice(assignableTypes = TransactionController.class)
public class TransactionControllerAdvice {
    @ExceptionHandler(CircularTransactionException.class)
    public ResponseEntity<?> circularTransaction() {
        return new ResponseEntity<>(Map.of(
                "error", "CIRCULAR_TRANSACTION",
                "message", "Circular transaction"),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> transactionNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> insufficientFunds() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
