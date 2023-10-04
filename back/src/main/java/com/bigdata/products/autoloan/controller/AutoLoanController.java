package com.bigdata.products.autoloan.controller;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.service.AutoLoanService;
import com.bigdata.products.common.CommonController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/autoloan")
@Tag(
        name = "Контроллер автокредитов",
        description = "Используется для добавления автокредитов в систему. " +
                "Может использоваться ТОЛЬКО администратором (ограничение будет добавлено позже)"
)
@Slf4j
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'USER')")
public class AutoLoanController implements CommonController<AutoLoanProduct> {

    private final AutoLoanService autoLoanService;

    @Operation(
            summary = "Регистрация автокредита",
            description = "Админ от банка регистрирует продукт с помощью этого метода"
    )
    @PostMapping("/register")
    public ResponseEntity<Integer> registerLending(
            @RequestBody @Parameter(name = "Параметры продукта") AutoLoanProduct autoLoanProduct
            ) {
        try {
            int id = autoLoanService.registerNewLending(autoLoanProduct);
            log.info("Lending {} was registered", id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            log.error("Lending {} wasn't registered, the reason is {}", autoLoanProduct.toString(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Удаление продукта",
            description = "Удаление продукта по его id админом банка"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLending(
            @PathVariable @Parameter(name = "id удаляемого продукта") Integer id
    ) {
        try {
            autoLoanService.deleteById(id);
            log.info("Lending {} was deleted", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Lending {} not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Получение продукта",
            description = "Получение продукта по id админом банка"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AutoLoanProduct> getLending(
            @PathVariable @Parameter(name = "id получаемого продукта") Integer id
    ) {
        try {
            AutoLoanProduct response = autoLoanService.getById(id);
            log.info("Found lending {}", id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            log.error("{} not found!", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Обновление продукта (изменение)",
            description = "Обновление по id админом банка"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Integer> updateLending(
            @PathVariable @Parameter(name = "id обновляемого продукта") Integer id,
            @RequestBody @Parameter(name = "Новые данные") AutoLoanProduct autoLoanProduct
    ) {
        try {
            autoLoanService.updateById(id, autoLoanProduct);
            log.info("Lending {} was updated", id);
            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) {
            log.error("Lending {} was not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
