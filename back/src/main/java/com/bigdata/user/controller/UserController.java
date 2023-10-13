package com.bigdata.user.controller;

import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
@Tag(
        name = "Пользовательский контроллер",
        description = "Позволяет совершать CRUD-операции с пользователями"
)
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получение пользователя по id",
        description = "Позволяет получить пользователя по id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'OPERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id
    ) {
        try {
            var userResponse = userService.getUserById(Integer.parseInt(id));
            log.info("{} was received.", id);
            return ResponseEntity.ok(userResponse);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Обновление пользователя",
            description = "Обновление пользователя по его идентификатору"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'OPERATOR', 'SUPER_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id,
            @RequestBody @Parameter(description = "Полученная информация о пользователе") Map<String, String> request
    ) {
        return ResponseEntity.ok(userService.updateUserById(Integer.parseInt(id), request));
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление пользователя по его идентификатору"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'OPERATOR', 'SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id
    ) {
        userService.deleteUserById(Integer.parseInt(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
