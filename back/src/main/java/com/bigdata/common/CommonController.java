package com.bigdata.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Tag(
        name = "Общий контроллер"
)
public class CommonController {
    @GetMapping(
            value = "/help.docs",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<?> getHelpDoc() {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/docs/ПСБ_Руководство_Пользователя.pdf");
            byte[] result = inputStream.readAllBytes();
            return ResponseEntity.ok(result);
        } catch (IOException r) {
            r.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
