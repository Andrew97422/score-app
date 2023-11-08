package com.bigdata.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(
            summary = "Получение руководства пользователя"
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

    @GetMapping(
            value = "/admin/help.docs",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @Operation(
            summary = "Получение руководства администратораа"
    )
    public ResponseEntity<?> getHelpAdminDoc() {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/docs/ПСБ_Руководство_Администратора.pdf");
            byte[] result = inputStream.readAllBytes();
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(
            value = "/favicon"
            //produces = MediaType.ALL
    )
    @ResponseBody
    public ResponseEntity<?> getFavicon() {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/icons/favicon.ico");
            byte[] result = inputStream.readAllBytes();
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/drom-favicon")
    public ResponseEntity<?> getDromFavicon() {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/icons/kisspng-ru-car-russia-website-windows-phone-mitsubishi-delica-5bed3d3822a870.540614801542274360142.ico");
            byte[] result = inputStream.readAllBytes();
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
