package com.bigdata.lending.controller;

import com.bigdata.lending.service.LendingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/lending")
@Tag(
        name = "Контроллер кредитных продуктов",
        description = "Используется для добавления кредитных продуктов в систему. " +
                "Может использоваться ТОЛЬКО администратором (ограничение будет добавлено позже)"
)
@Slf4j
public class LendingController {
    private final LendingService lendingService;
}
