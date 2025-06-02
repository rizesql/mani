package org.pao.mani.modules.users.web;

public record CreateUserRequest(
        String username,
        String email
) {
}
