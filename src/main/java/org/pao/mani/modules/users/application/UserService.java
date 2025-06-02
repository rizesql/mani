package org.pao.mani.modules.users.application;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.audit.domain.Audit;
import org.pao.mani.modules.users.core.*;
import org.pao.mani.modules.users.core.exceptions.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Audit("users::get")
    public User get(UserId userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Audit("users::all")
    public List<User> all() {
        return userRepository.findAll();
    }

    @Audit("users::create")
    public UserId create(Username username, Email email) {
        var existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new EmailTakenException(email);
        }

        var now = Timestamp.now();
        var user = User.create(username, email, now);

        userRepository.save(user);
        return user.id();
    }

    @Audit("users::change_username")
    public void changeUsername(UserId id, Username newUsername) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        var now = Timestamp.now();
        user.setUsername(newUsername, now);

        userRepository.save(user);
    }

    @Audit("users::change_email")
    public void changeEmail(UserId id, Email newEmail) {
        var existing = userRepository.findByEmail(newEmail);
        if (existing.isPresent()) {
            throw new EmailTakenException(newEmail);
        }

        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        var now = Timestamp.now();
        user.setEmail(newEmail, now);

        userRepository.save(user);
    }

    @Audit("users::delete")
    public void delete(UserId id) {
        var existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(id);
    }
}
