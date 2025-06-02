package org.pao.mani.modules.cards;

import org.pao.mani.modules.cards.core.CardRepository;
import org.pao.mani.modules.cards.infra.jdbc.JdbcCardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class CardsConfig {
    @Bean
    public CardRepository cardRepository(JdbcClient jdbcClient) {
        return JdbcCardRepository.instance(jdbcClient);
    }
}
