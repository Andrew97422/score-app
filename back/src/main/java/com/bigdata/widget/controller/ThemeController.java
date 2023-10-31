package com.bigdata.widget.controller;

import com.bigdata.widget.model.dto.ThemeRequest;
import com.bigdata.widget.model.dto.ThemeResponse;
import com.bigdata.widget.service.ThemeWidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/widget/themes")
public class ThemeController {

    private final ThemeWidgetService widgetService;

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        return ResponseEntity.ok(widgetService.getAllThemes());
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
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

    @PatchMapping("/{id}")
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
