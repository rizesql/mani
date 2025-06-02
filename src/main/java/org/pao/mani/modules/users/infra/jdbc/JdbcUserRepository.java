package org.pao.mani.modules.users.infra.jdbc;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.users.core.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserRepository implements UserRepository {
    private static JdbcUserRepository instance = null;

    private final JdbcClient jdbcClient;

    private JdbcUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public static synchronized UserRepository instance(JdbcClient jdbcClient) {
        if (instance == null) {
            instance = new JdbcUserRepository(jdbcClient);
        }
        return instance;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jdbcClient
                .sql("""
                     SELECT id, username, email, created_at, updated_at
                     FROM users
                     WHERE id = :id
                     """)
                .param("id", id.value())
                .query(rowMapper)
                .optional();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jdbcClient
                .sql("""
                     SELECT id, username, email, created_at, updated_at
                     FROM users
                     WHERE email = :email
                     """)
                .param("email", email.value())
                .query(rowMapper)
                .optional();
    }

    @Override
    public List<User> findAll() {
        return jdbcClient
                .sql("""
                     SELECT id, username, email, created_at, updated_at
                     FROM users
                     """)
                .query(rowMapper)
                .list();
    }

    @Override
    public void save(User user) {
        int updatedRows = jdbcClient
                .sql("""
                     UPDATE users
                     SET username = :username,
                         email = :email,
                         updated_at = :updated_at
                     WHERE id = :id
                     """)
                .param("id", user.id().value())
                .param("username", user.username().value())
                .param("email", user.email().value())
                .param("updated_at", user.updatedAt().value())
                .update();

        if (updatedRows == 0) {
            jdbcClient
                    .sql("""
                         INSERT INTO users (id, username, email, created_at, updated_at)
                         VALUES (:id, :username, :email, :created_at, :updated_at)
                         """)
                    .param("id", user.id().value())
                    .param("username", user.username().value())
                    .param("email", user.email().value())
                    .param("created_at", user.createdAt().value())
                    .param("updated_at", user.updatedAt().value())
                    .update();
        }
    }

    @Override
    public void delete(UserId id) {
        jdbcClient
                .sql("DELETE FROM users WHERE id = :id")
                .param("id", id.value())
                .update();
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> new User(
            new UserId(rs.getObject("id", UUID.class)),
            new Username(rs.getString("username")),
            new Email(rs.getString("email")),
            new Timestamp(rs.getLong("created_at")),
            new Timestamp(rs.getLong("updated_at"))
    );
}
