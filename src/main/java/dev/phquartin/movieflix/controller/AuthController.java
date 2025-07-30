package dev.phquartin.movieflix.controller;

import dev.phquartin.movieflix.controller.request.UserRequest;
import dev.phquartin.movieflix.controller.response.UserResponse;
import dev.phquartin.movieflix.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        UserResponse saved = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
