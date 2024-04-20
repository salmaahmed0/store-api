package com.example.store.model.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductValidationRequestDTO {
    @NotBlank(message = "product code is mandatory")
    private String productCode;
    @Positive
    private int quantity;
}
