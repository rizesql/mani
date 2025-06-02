package org.pao.mani.modules.transactions.web;

import org.pao.mani.core.*;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.transactions.application.TransactionService;
import org.pao.mani.modules.transactions.core.TransactionId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> create(@RequestBody CreateTransactionRequest req) {
        var res = transactionService.create(
                new AccountId(req.creditAccountId()),
                new AccountId(req.debitAccountId()),
                new Money(req.amount(), Currency.from(req.currency()))
        );
        return ResponseEntity.ok(res);
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<?> lookup(@PathVariable("transactionId") UUID transactionId) {
        var res = transactionService.get(new TransactionId(transactionId));
        return ResponseEntity.ok(TransactionResponse.from(res));
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable("accountId") UUID accountId) {
        var res = transactionService.get(new AccountId(accountId));
        return ResponseEntity.ok(res.stream().map(TransactionResponse::from));
    }
}
