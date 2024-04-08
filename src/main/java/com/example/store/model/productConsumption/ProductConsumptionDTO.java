package com.example.store.model.productConsumption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductConsumptionDTO {
    private String  productCode;
    private String orderCode;
    private int quantityConsumed;
    Date consumedAt;
}
