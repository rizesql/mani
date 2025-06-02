package org.pao.mani.modules.users.web;

import org.pao.mani.modules.users.application.UserService;
import org.pao.mani.modules.users.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(userService.all().stream().map(UserResponse::from));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest req) {
        var res = userService.create(
                new Username(req.username()),
                new Email(req.email())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") UUID userId) {
        var res = userService.get(new UserId(userId));
        return ResponseEntity.ok(UserResponse.from(res));
    }


    @PatchMapping("/{userId}/email")
    public ResponseEntity<?> changeEmail(
            @PathVariable("userId") UUID userId,
            @RequestBody ChangeEmailRequest req
    ) {
        userService.changeEmail(
                new UserId(userId),
                new Email(req.email())
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/username")
    public ResponseEntity<?> changeUsername(
            @PathVariable("userId") UUID userId,
            @RequestBody ChangeUsernameRequest req
    ) {
        userService.changeUsername(
                new UserId(userId),
                new Username(req.username())
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable("userId") UUID userId) {
        userService.delete(new UserId(userId));
        return ResponseEntity.ok().build();
    }
}
