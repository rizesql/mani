package org.pao.mani.queries;

import org.pao.mani.data.CardRepository;
import org.pao.mani.domain.Account;
import org.pao.mani.domain.AccountCards;

public class GetCards {
    public record Query(Account.Id id) { }

    private final CardRepository cardRepository;

    public GetCards(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public AccountCards run(Query query) {
        return cardRepository.get(query.id);
    }
}
