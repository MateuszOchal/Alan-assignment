package com.alansystems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("value")
    private double value;
}
