package org.pao.mani.modules.cards.infra.jdbc;

import org.pao.mani.lib.StreamUtils;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.cards.core.*;
import org.pao.mani.core.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.*;

public class JdbcCardRepository implements CardRepository {
    private static JdbcCardRepository instance = null;

    private final JdbcClient jdbcClient;

    private JdbcCardRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public static synchronized JdbcCardRepository instance(JdbcClient jdbcClient) {
        if (instance == null) {
            instance = new JdbcCardRepository(jdbcClient);
        }
        return instance;
    }

    @Override
    public AccountCards get(AccountId id) {
        var cards = jdbcClient
                .sql("""
                     SELECT id, account_id, iban, opened_at, pin
                     FROM cards
                     WHERE account_id = :account_id
                     """)
                .param("account_id", id.value())
                .query(rowMapper)
                .list();

        return new AccountCards(
                StreamUtils.filterMap(cards.stream(), Card::physical).findFirst(),
                StreamUtils.filterMap(cards.stream(), Card::virtual).findFirst()
        );
    }

    @Override
    public Optional<? extends Card> findById(CardId id) {
        return jdbcClient
                .sql("""
                     SELECT c.id, c.account_id, c.iban, c.opened_at, c.pin
                     FROM cards c
                     WHERE c.id = :id
                     """)
                .param("id", id.value())
                .query(rowMapper)
                .optional();
    }

    @Override
    public void create(Card card) {
        jdbcClient
                .sql("""
                     INSERT INTO cards (id, account_id, iban, opened_at, pin)
                     VALUES (:id, :account_id, :iban, :opened_at, :pin)
                     """)
                .param("id", card.id().value())
                .param("account_id", card.accountId().value())
                .param("iban", card.iban().value())
                .param("opened_at", card.openedAt().value())
                .param("pin", card instanceof PhysicalCard pc ? pc.pin().value() : null)
                .update();
    }

    @Override
    public void delete(CardId cardId) {
        jdbcClient
                .sql("DELETE FROM cards WHERE id = :id")
                .param("id", cardId.value())
                .update();
    }

    @Override
    public Set<Pin> getPins(CardId id) {
        return jdbcClient
                .sql("""
                     SELECT pp.pin
                     FROM previous_pins pp
                     where pp.card_id = :card_id
                     """)
                .param("card_id", id.value())
                .query((rs, rowNum) -> new Pin(rs.getString("pin")))
                .set();
    }

    @Override
    public void changePin(CardId cardId, Pin prevPin, Pin newPin) {
        jdbcClient
                .sql("""
                     INSERT INTO previous_pins (card_id, pin, added_at)
                     VALUES (:card_id, :pin, :added_at)
                     """)
                .param("card_id", cardId.value())
                .param("pin", prevPin.value())
                .param("added_at", Timestamp.now().value())
                .update();

        jdbcClient
                .sql("""
                     UPDATE cards
                     SET pin = :new_pin
                     WHERE id = :id
                     """)
                .param("new_pin", newPin.value())
                .param("id", cardId.value())
                .update();
    }

    private final RowMapper<Card> rowMapper = (rs, rowNum) -> {
        var cardId = new CardId(rs.getObject("id", UUID.class));
        var accId = new AccountId(rs.getObject("account_id", UUID.class));
        var ibanObj = new Iban(rs.getString("iban"));
        var timestamp = new Timestamp(rs.getLong("opened_at"));
        var rawPin = rs.getString("pin");

        if (rawPin != null) {
            return new PhysicalCard(cardId, accId, ibanObj, new Pin(rawPin), timestamp);
        }

        return new VirtualCard(cardId, accId, ibanObj, timestamp);
    };

}
