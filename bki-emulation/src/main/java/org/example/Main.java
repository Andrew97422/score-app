package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class Main {
    private final Service service;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    record UserInfo(String firstName, String lastName, String surName) {}

    @PostMapping("/")
    public UserInfo hello(
            @RequestBody UserInfo c
    ) {
        System.out.println("Получен " + c.toString());
        return c;
    }
}