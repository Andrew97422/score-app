package com.bigdata.products.consumer.model.dto;

import com.bigdata.products.common.model.CommonProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerProduct extends CommonProduct {

    @Schema(name = "discount", description = "Скидка",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String discount;
}
