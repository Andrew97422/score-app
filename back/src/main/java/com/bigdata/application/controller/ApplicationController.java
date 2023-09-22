package com.bigdata.application.controller;

import com.bigdata.application.model.dto.ApplicationByTypeResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.service.ApplicationService;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
@Tag(
        name = "Контроллер заявок",
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
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> registerApplicationWithAuthentication(
            @RequestBody @Parameter(name = "Заявка") ScoringApplicationWithAuthRequest request,
            @AuthenticationPrincipal UserEntity user
    ) {
        try {
            log.info("Received application {}, birthday {} is specified", request, user.getBirthday());
            applicationService.addNewApplicationWithAuth(request, user);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Exception was caught: ", e);
            log.error("User: {}", user.getLogin());
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
    public ResponseEntity<HttpStatus> registerApplicationWithoutAuthentication(
        @RequestBody @Parameter(name = "Заявка") ScoringApplicationWithoutAuthRequest request
    ) {
        log.info("Received application {}, birthday {} is specified", request, request.getBirthday());
        try {
            applicationService.addNewApplicationWithoutAuth(request);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Have an error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    record ApplicationResponse(int count, List<ApplicationByTypeResponse> applications) {}

    @Operation(
            summary = "Получение списка заявок",
            description = "Получение списка заявок по типу type"
    )
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApplicationResponse> getApplicationsByType(
            @RequestParam (name = "type") LendingType type,
            @AuthenticationPrincipal UserEntity user
    ) {
        List<ApplicationByTypeResponse> responses =
                applicationService.getApplicationsList(type, user);
        return ResponseEntity.ok(new ApplicationResponse(responses.size(), responses));
    }

    @Operation(
            summary = "Удаление заявки",
            description = "Удаление заявки по её id"
    )
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> deleteApplicationById(
        @RequestParam (name = "id") Integer id,
        @AuthenticationPrincipal UserEntity user
    ) {
        log.info("Start deleting {}", id);
        applicationService.deleteApplication(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Создание PDF-файла",
            description = "Создаёт PDF-файл по id заявке"
    )
    @GetMapping("/create_pdf")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<byte[]> createPdfDocument(
        @RequestParam (name = "id") Integer id
    ) {
        log.info("Forming pdf");
        try {
            byte[] content = applicationService.formPdfDocument(id);
            return content.length == 1 || content.length == 0
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Some problem with forming pdf: {}", e.getLocalizedMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
