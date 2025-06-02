package org.pao.mani.modules.accounts.infra.jdbc;

import org.pao.mani.core.*;
import org.pao.mani.core.Currency;
import org.pao.mani.modules.accounts.core.*;
import org.pao.mani.modules.users.core.UserId;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.*;

public class JdbcAccountRepository implements AccountRepository {
    private static JdbcAccountRepository instance = null;

    private final JdbcClient jdbcClient;

    private JdbcAccountRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public static synchronized JdbcAccountRepository instance(JdbcClient jdbcClient) {
        if (instance == null) {
            instance = new JdbcAccountRepository(jdbcClient);
        }
        return instance;
    }

    public Optional<Account> findById(AccountId id) {
        return jdbcClient
                .sql("""
                     SELECT id, owner_id, amount, currency, closed_at 
                     FROM accounts 
                     WHERE id = :id
                     """)
                .param("id", id.value())
                .query(rowMapper)
                .optional();
    }

    public void create(Account.Active account) {
        jdbcClient
                .sql("""
                     INSERT INTO accounts (id, owner_id, amount, currency, closed_at)
                     VALUES (:id, :owner_id, :amount, :currency, NULL)
                     """)
                .param("id", account.id().value())
                .param("owner_id", account.ownerId().value())
                .param("amount", account.credit().amount())
                .param("currency", account.credit().currency().into())
                .update();
    }

    public void close(Account.Closed account) {
        jdbcClient
                .sql("""
                     UPDATE accounts 
                     SET closed_at = :closed_at
                     WHERE id = :id
                     """)
                .param("closed_at", account.closedAt().value())
                .param("id", account.id().value())
                .update();
    }

    private final RowMapper<Account> rowMapper = (rs, rowNum) -> {
        var id = new AccountId(rs.getObject("id", UUID.class));

        return Timestamp.of(rs.getLong("closed_at"))
                .map(closedAt -> (Account) new Account.Closed(id, closedAt))
                .orElse(new Account.Active(
                        id,
                        new UserId(rs.getObject("owner_id", UUID.class)),
                        new Money(
                                rs.getBigDecimal("amount"),
                                Currency.from(rs.getString("currency"))
                        )
                ));
    };
}
