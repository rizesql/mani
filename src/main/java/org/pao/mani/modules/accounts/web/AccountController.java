package org.pao.mani.modules.accounts.web;

import org.pao.mani.core.*;
import org.pao.mani.modules.accounts.application.AccountService;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.users.core.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateAccountRequest req) {
        var res = accountService.create(
                new UserId(req.ownerId()),
                new Money(req.amount(), Currency.from(req.currency()))
        );
        return ResponseEntity.ok(CreateAccountResponse.from(res));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> lookup(@PathVariable("accountId") UUID id) {
        var res = accountService.lookup(new AccountId(id));
        return ResponseEntity.ok(LookupAccountResponse.from(res));
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> balance(@PathVariable("accountId") UUID id) {
        var res = accountService.balance(new AccountId(id));
        return ResponseEntity.ok(AccountBalanceResponse.from(res));
    }

    @PostMapping("/{accountId}/close")
    public ResponseEntity<?> close(@PathVariable("accountId") UUID id) {
        accountService.close(new AccountId(id));
        return ResponseEntity.accepted().build();
    }
}
