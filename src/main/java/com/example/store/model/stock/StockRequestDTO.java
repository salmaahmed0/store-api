package com.example.store.model.stock;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDTO {
    @NotBlank(message = "product code is mandatory")
    private String productCode;
    @NotNull
    @Positive
    private Long storeId;
    @Positive
    @NotNull
    private Integer quantity;
}
