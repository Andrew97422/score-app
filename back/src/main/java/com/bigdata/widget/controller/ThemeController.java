package com.bigdata.widget.controller;

import com.bigdata.widget.model.dto.ThemeRequest;
import com.bigdata.widget.model.dto.ThemeResponse;
import com.bigdata.widget.service.ThemeWidgetService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(
        name = "Контроллер темы",
        description = "Весь CRUD темы"
)
@RequestMapping("/api/v1/widget/themes")
public class ThemeController {

    private final ThemeWidgetService widgetService;

    @GetMapping
    @Operation(
            summary = "Получение всех тем"
    )
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        return ResponseEntity.ok(widgetService.getAllThemes());
    }

    @PostMapping
    @Operation(
            summary = "Добавление новой темы"
    )
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> addNewTheme(
            @RequestBody ThemeRequest request
    ) {
        try {
            widgetService.addNewTheme(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "Получение темы по id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponse> getTheme(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.ok(widgetService.getThemeById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "Удаление темы по id"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> deleteTheme(
            @PathVariable Integer id
    ) {
        try {
            widgetService.deleteThemeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Обновление темы по id"
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> updateTheme(
            @PathVariable Integer id, @RequestBody ThemeRequest request
    ) {
        try {
            widgetService.updateThemeById(id, request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
