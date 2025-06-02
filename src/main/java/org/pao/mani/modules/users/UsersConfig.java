package org.pao.mani.modules.users;

import org.pao.mani.modules.users.core.UserRepository;
import org.pao.mani.modules.users.infra.jdbc.JdbcUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class UsersConfig {
    @Bean
    public UserRepository userRepository(JdbcClient jdbcClient) {
        return JdbcUserRepository.instance(jdbcClient);
    }
}
