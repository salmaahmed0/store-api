package com.example.store.model.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StockResponseDTO {
    long stockId;
    private String productCode;
    private Long storeId;
    private Integer quantity;
    private int consumedQuantity;
    private LocalDate creationDate;
}
