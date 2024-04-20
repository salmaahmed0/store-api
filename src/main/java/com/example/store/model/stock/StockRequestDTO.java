package com.example.store.model.stock;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
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
