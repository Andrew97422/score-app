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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin
@Tag(
        name = "Контроллер аутентификации",
        description = "Контроллер для регистрации и логина пользователей"
)
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Создание нового пользователя",
            description = "Добавляет нового пользователя, возвращает токен, который надо приставлять в хедерах."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> createUser(
            @RequestBody @Parameter(description = "Полученная информация о пользователе") RegisterRequest registerRequest
    ) {
        try {
            var token = authService.register(registerRequest);
            log.info("User with token {} was registered.", token.getToken());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Аутентификация нового пользователя",
            description = "Аутентифицирует нового пользователя, возвращает токен, который надо приставлять в хедерах."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> doLogin(
            @RequestBody LoginRequest loginRequest
    ) {
        try {
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
