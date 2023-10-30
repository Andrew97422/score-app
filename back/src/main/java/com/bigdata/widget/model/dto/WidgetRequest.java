package com.bigdata.widget.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WidgetRequest {
    private double interestRate;
    private String color;
    private String font;
    private String name;
}
