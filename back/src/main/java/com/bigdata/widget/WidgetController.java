package com.bigdata.widget;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/widget")
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping("/{id}")
    public ResponseEntity<WidgetResponse> getWidget(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(widgetService.getWidget(id));
    }

    @GetMapping
    public ResponseEntity<List<WidgetResponse>> getAllWidgets() {
        return ResponseEntity.ok(widgetService.getAllWidgets());
    }

    @PostMapping("/settings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> setWidget(
            @RequestBody Map<String, String> params
    ) {
        try {
            widgetService.setWidget(params);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ignored) {}
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
