package com.example.store.model.productConsumption;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOrderDTO {
    private String productCode;
    private String orderCode;
}
