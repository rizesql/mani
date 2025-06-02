package org.pao.mani.modules.users.core;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    List<User> findAll();
    void save(User user);
    void delete(UserId id);
}
