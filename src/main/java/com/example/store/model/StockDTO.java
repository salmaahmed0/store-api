package com.example.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private String productCode;
    private long storeId;
    private int quantity;
    private int consumedQuantity;
    private LocalDate creationDate;
}
