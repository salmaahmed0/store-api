package com.example.store.model.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDTO {
    long stockId;
    private String productCode;
    private Long storeId;
    private Integer quantity;
    private int consumedQuantity;
    private LocalDate creationDate;
}
