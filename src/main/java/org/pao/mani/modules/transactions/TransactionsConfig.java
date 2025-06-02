package org.pao.mani.modules.transactions;

import org.pao.mani.modules.transactions.core.TransactionRepository;
import org.pao.mani.modules.transactions.infra.jdbc.JdbcTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class TransactionsConfig {
    @Bean
    public TransactionRepository transactionRepository(JdbcClient jdbcClient) {
        return JdbcTransactionRepository.instance(jdbcClient);
    }
}
