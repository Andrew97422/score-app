package com.bigdata.application.controller;

import com.bigdata.application.model.dto.ApplicationForScoringRequest;
import com.bigdata.application.service.ApplicationService;
import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
@Tag(name = "Контроллер заявок",
        description = "Контроллер для работы с поступающими заявками на скоринг"
)
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(
            summary = "Регистрация заявки",
            description = "Регистрация заявки на скоринг, " +
                    "работает с авторизованными пользователями, " +
                    "предоставляет все варианты кредитования, " +
                    "результат будет прислан на email."
    )
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerNewApplication(
            @RequestBody @Parameter(name = "Заявка") ApplicationForScoringRequest request,
            @AuthenticationPrincipal UserEntity user
    ) {
        try {
            applicationService.addNewApplication(request, user);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Регистрация заявки",
            description = "Регистрация заявки на скоринг, " +
                    "работает с неавторизованными пользователями, " +
                    "предоставляет все варианты кредитования, " +
                    "результат будет прислан на email."
    )
    @PostMapping("/noauth/register")
    public ResponseEntity<HttpStatus> registerNewApplicationWithoutAuthentication(
        @RequestBody @Parameter(name = "Заявка") ApplicationForScoringRequest request
    ) {
        log.info("Received application {}, birthday {} is specified", request, request.getBirthday());
        try {
            applicationService.addNewApplication(request,
                    UserEntity.builder().birthday(request.getBirthday()).build());
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Have an error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
