package com.bigdata.products.autoloan.model.dto;

import com.bigdata.products.common.CommonProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AutoLoanProduct extends CommonProduct {

    @Schema(name = "car_mileage", description = "Пробег автомобиля",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mileage;
}
