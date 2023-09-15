package com.bigdata.auth.controller;

import com.bigdata.auth.model.AuthenticationResponse;
import com.bigdata.auth.model.LoginRequest;
import com.bigdata.auth.model.RegisterRequest;
import com.bigdata.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
@Tag(
        name = "Контроллер аутентификации",
        description = "Контроллер для регистрации и логина пользователей"
)
public class AuthController {

    private final AuthService authService;

    @Operation(
        summary = "Создание нового пользователя",
        description = "Добавляет нового пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(
            @RequestBody @Parameter(description = "Полученная информация о пользователе") RegisterRequest registerRequest
    ) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return e.getMessage().endsWith("exists") ?
                    ResponseEntity.status(HttpStatus.CONFLICT).build() :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Аутентификация нового пользователя",
            description = "Аутентифицирует нового пользователя, возвращает токен, который надо приставлять в хедерах и его айди."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> doLogin(
            @RequestBody LoginRequest loginRequest
    ) {
        try {
            log.info("Logging user {}...", loginRequest.getUsername());
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthenticationResponse());
        }
    }
}
