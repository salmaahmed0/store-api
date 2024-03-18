package com.example.store.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConsumption {
    @NotBlank
    private String productCode;
    @Positive
    private int orderId;
    @Positive
    private int quantity;
    @NotBlank
    private String consumedAt;
}
