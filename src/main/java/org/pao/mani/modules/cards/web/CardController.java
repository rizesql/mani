package org.pao.mani.modules.cards.web;

import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.cards.application.CardService;
import org.pao.mani.modules.cards.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/{accountId}/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("")
    public ResponseEntity<?> add(
            @PathVariable("accountId") UUID id,
            @RequestBody AddCardRequest req
    ) {
        var res = cardService.add(Card.of(
                new AccountId(id),
                new Iban(req.iban()), req.pin().map(Pin::new)
        ));

        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<?> get(@PathVariable("accountId") UUID accountId) {
        var res = cardService.get(new AccountId(accountId));
        return ResponseEntity.ok(GetCardsResponse.from(res));
    }

    @PatchMapping("")
    public ResponseEntity<?> changePin(
            @PathVariable("accountId") UUID accountId,
            @RequestBody ChangePinRequest req
    ) {
        cardService.changePin(new AccountId(accountId), new Pin(req.pin()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/revoke")
    public ResponseEntity<?> revoke(
            @PathVariable("accountId") UUID accountId,
            @PathVariable("cardId") UUID cardId
    ) {
        cardService.revoke(new AccountId(accountId), new CardId(cardId));
        return ResponseEntity.ok().build();
    }
}
