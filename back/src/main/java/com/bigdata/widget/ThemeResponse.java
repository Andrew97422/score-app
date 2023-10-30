package com.bigdata.widget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThemeResponse {
    private Integer id;
    private String color;
    private String font;
    private String name;
}
