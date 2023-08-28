package com.bigdata.user.controller;

import com.bigdata.user.model.dto.UserInfo;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Пользовательский контроллер",
        description = "Позволяет совершать CRUD-операции с пользователями"
)
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получение пользователя по id",
        description = "Позволяет получить пользователя по id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id
    ) {
        try {
            var userResponse = userService.getUserById(Integer.parseInt(id));
            log.info("{} was received.", id);
            return ResponseEntity.ok(userResponse);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Создание нового пользователя",
            description = "Добавляет нового пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<Integer> createUser(
            @RequestBody @Parameter(description = "Полученная информация о пользователе") UserInfo userInfo
    ) {
        try {
            var id = userService.createNewUser(userInfo);
            log.info("User {} was registered.", id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Integer> updateUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id,
            @RequestBody @Parameter(description = "Полученная информация о пользователе") UserInfo userInfo
    ) {

        return ResponseEntity.ok(userService.updateUserById(Integer.parseInt(id), userInfo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя") String id
    ) {
        userService.deleteUserById(Integer.parseInt(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
