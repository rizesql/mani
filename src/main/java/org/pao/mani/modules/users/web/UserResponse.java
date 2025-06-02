package org.pao.mani.modules.users.web;

import org.pao.mani.modules.users.core.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String createdAt,
        String updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.id().value(),
                user.username().value(),
                user.email().value(),
                user.createdAt().toInstant().toString(),
                user.updatedAt().toInstant().toString()
        );
    }
}
