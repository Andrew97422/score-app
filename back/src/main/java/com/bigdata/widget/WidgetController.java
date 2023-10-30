package com.bigdata.widget;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/widget")
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping("/getFirst")
    public ResponseEntity<WidgetResponse> getWidget() {
        return ResponseEntity.ok(widgetService.getWidget());
    }

    @GetMapping
    public ResponseEntity<List<WidgetResponse>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @PostMapping("/settings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> setWidget(
            @RequestBody WidgetRequest request
    ) {
        try {
            widgetService.setWidget(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        return ResponseEntity.ok(widgetService.getAllThemes());
    }

    @PostMapping("/themes")
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

    @GetMapping("/themes/{id}")
    public ResponseEntity<ThemeResponse> getTheme(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.ok(widgetService.getThemeById(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/themes/{id}")
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

    @PatchMapping("/themes/{id}")
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
