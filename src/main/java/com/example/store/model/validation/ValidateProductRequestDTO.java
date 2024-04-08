package com.example.store.model.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateProductRequestDTO {
    @NotBlank(message = "product code is mandatory")
    private String productCode;
    @Positive
    private int quantity;
}
