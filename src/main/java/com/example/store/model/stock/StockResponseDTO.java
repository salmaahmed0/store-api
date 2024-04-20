package com.example.store.model.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StockResponseDTO {
    long stockId;
    @NotBlank(message = "product code is mandatory")
    private String productCode;
    @NotNull
    @Positive
    private Long storeId;
    @Positive
    @NotNull
    private Integer quantity;
    @Positive
    private int consumedQuantity;
    private LocalDate creationDate;
}
