package com.example.store.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    @NotBlank(message = "product code is mandatory")
    private String productCode;
    @NotEmpty(message = "storeId is mandatory")
    @Positive
    private long storeId;
    @NotEmpty(message = "quantity is mandatory")
    @Positive
    private int quantity;
    @Null
    private int consumedQuantity;
    @Null
    private LocalDate creationDate;
}
