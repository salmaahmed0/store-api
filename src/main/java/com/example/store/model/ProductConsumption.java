package com.example.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConsumption {
    private String productCode;
    private int orderId;
    private int quantity;
    private String consumedAt;
}
