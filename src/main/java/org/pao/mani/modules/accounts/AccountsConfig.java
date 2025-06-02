package org.pao.mani.modules.accounts;

import org.pao.mani.modules.accounts.core.AccountRepository;
import org.pao.mani.modules.accounts.infra.jdbc.JdbcAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class AccountsConfig {
    @Bean
    public AccountRepository accountRepository(JdbcClient jdbcClient) {
        return JdbcAccountRepository.instance(jdbcClient);
    }
}
