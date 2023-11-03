package com.bigdata.widget.controller;

import com.bigdata.widget.model.dto.WidgetRequest;
import com.bigdata.widget.model.dto.WidgetResponse;
import com.bigdata.widget.service.WidgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/widget")
@Tag(
        name = "Контроллер виджета",
        description = "Контроллер для работы с виджетом - его изменениями, получением и тд"
)
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping("/getFirst")
    @Operation(
            summary = "Получение первого виджета",
            description = "Метод позволяет получать виджет с id = 1"
    )
    public ResponseEntity<WidgetResponse> getWidget() {
        return ResponseEntity.ok(widgetService.getWidget());
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех виджетов",
            description = "Метод позволяет получать списко всех виджетов"
    )
    public ResponseEntity<List<WidgetResponse>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @PostMapping("/settings")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Установка настроек виджета с id = 1",
            description = "Метод принимает запрос с настройками для виджета с id = 1 и устанавливает их"
    )
    public ResponseEntity<HttpStatus> setWidget(
            @RequestBody @Parameter(name = "Параметры виджета") WidgetRequest request
    ) {
        try {
            widgetService.setWidget(request, 1);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/{id}/settings")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Установка настрок виджета",
            description = "Метод принимает запрос с настройками и id"
    )
    public ResponseEntity<HttpStatus> setWidget(
            @RequestBody @Parameter(name = "Параметры виджета") WidgetRequest request,
            @PathVariable @Parameter(name = "Id виджета") Integer id
    ) {
        try {
            widgetService.setWidget(request, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
