package org.pao.mani.modules.users.core;

import org.pao.mani.core.Timestamp;
import org.pao.mani.modules.users.core.exceptions.SameEmailException;
import org.pao.mani.modules.users.core.exceptions.SameUsernameException;

public final class User {
    private final UserId id;
    private Username username;
    private Email email;

    private final Timestamp createdAt;
    private Timestamp updatedAt;

    public User(UserId id, Username username, Email email, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(Username username, Email email, Timestamp createdAt) {
        return new User(UserId.generate(), username, email, createdAt, createdAt);
    }

    public UserId id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public Email email() {
        return email;
    }

    public Timestamp createdAt() {
        return createdAt;
    }

    public Timestamp updatedAt() {
        return updatedAt;
    }

    public void setEmail(Email email, Timestamp updatedAt) {
        if (email.equals(this.email)) {
            throw new SameEmailException();
        }

        this.email = email;
        this.updatedAt = updatedAt;
    }

    public void setUsername(Username username, Timestamp updatedAt) {
        if (username.equals(this.username)) {
            throw new SameUsernameException();
        }

        this.username = username;
        this.updatedAt = updatedAt;
    }
}
