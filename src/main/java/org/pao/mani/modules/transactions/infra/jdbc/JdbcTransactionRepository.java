package org.pao.mani.modules.transactions.infra.jdbc;

import org.pao.mani.core.*;
import org.pao.mani.core.Currency;
import org.pao.mani.modules.accounts.core.AccountId;
import org.pao.mani.modules.transactions.core.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.*;

public class JdbcTransactionRepository implements TransactionRepository {
    private static JdbcTransactionRepository instance = null;

    private final JdbcClient jdbcClient;

    private JdbcTransactionRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public static synchronized JdbcTransactionRepository instance(JdbcClient jdbcClient) {
        if (instance == null) {
            instance = new JdbcTransactionRepository(jdbcClient);
        }
        return instance;
    }

    @Override
    public void create(Transaction transaction) {
        jdbcClient
                .sql("""
                     INSERT INTO transactions (id, debit_account_id, credit_account_id, amount, currency, timestamp)
                     VALUES (:id, :debit_account_id, :credit_account_id, :amount, :currency, :timestamp)
                     """)
                .param("id", transaction.id().value())
                .param("debit_account_id", transaction.debitAccountId().value())
                .param("credit_account_id", transaction.creditAccountId().value())
                .param("amount", transaction.amount().amount())
                .param("currency", transaction.amount().currency().into())
                .param("timestamp", transaction.timestamp().value())
                .update();
    }

    @Override
    public Optional<Transaction> findById(TransactionId transactionId) {
        return jdbcClient
                .sql("""
                     SELECT id, debit_account_id, credit_account_id, amount, currency, timestamp
                     FROM transactions 
                     WHERE id = :id
                     """)
                .param("id", transactionId.value())
                .query(transactionRowMapper)
                .optional();
    }

    @Override
    public List<Transaction> get(AccountId accountId) {
        return jdbcClient
                .sql("""
                     SELECT id, debit_account_id, credit_account_id, amount, currency, timestamp
                     FROM transactions
                     WHERE (debit_account_id = :account_id OR credit_account_id = :account_id)
                     ORDER BY timestamp DESC
                     """)
                .param("account_id", accountId.value())
                .query(transactionRowMapper)
                .list();

    }

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> new Transaction(
            new TransactionId(rs.getObject("id", UUID.class)),
            new AccountId(rs.getObject("debit_account_id", UUID.class)),
            new AccountId(rs.getObject("credit_account_id", UUID.class)),
            new Money(
                    rs.getBigDecimal("amount"),
                    Currency.from(rs.getString("currency"))
            ),
            new Timestamp(rs.getLong("timestamp"))
    );
}
