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

    @GetMapping("/get")
    public ResponseEntity<WidgetResponse> getWidget() {
        return ResponseEntity.ok(widgetService.getWidget());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WidgetResponse>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @PostMapping("/settings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> setWidget(
            @RequestBody String color
    ) {
        try {
            widgetService.setWidget(color);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
