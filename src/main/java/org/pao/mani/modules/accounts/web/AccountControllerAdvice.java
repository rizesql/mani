package org.pao.mani.modules.accounts.web;

import org.pao.mani.modules.accounts.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ControllerAdvice(assignableTypes = AccountController.class)
public class AccountControllerAdvice {
    @ExceptionHandler(AccountClosedException.class)
    public ResponseEntity<?> handleAccountClosed(AccountClosedException ex) {
        var body = Map.of(
                "error", "ACCOUNT_CLOSED",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.GONE).body(body);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
