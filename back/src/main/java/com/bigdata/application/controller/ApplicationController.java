package com.bigdata.application.controller;

import com.bigdata.application.model.dto.ApplicationForScoringRequest;
import com.bigdata.application.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
@Tag(name = "Контроллер заявок", description = "Контроллер для работы с поступающими заявками на скоринг")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(
            summary = "Регистрация заявки",
            description = "Регистрация заявки на скоринг"
    )
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerNewApplication(
            @RequestBody @Parameter(name = "Заявка") ApplicationForScoringRequest request
    ) {
        applicationService.addNewApplication(request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


}
