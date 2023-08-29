package com.bigdata.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Scoring Application",
                description = "Application for scoring",
                version = "1.0.0",
                contact = @Contact(
                        name = "BIGdata",
                        url = "https://gitlab.fintechhub.ru/bigdata/score-app"
                )
        )
)
public class OpenApiConfig {
}
