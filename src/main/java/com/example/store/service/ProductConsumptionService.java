package com.example.store.service;

import com.example.store.model.productConsumption.ProductConsumptionDTO;
import com.example.store.model.productConsumption.ProductOrderDTO;

import java.util.List;

public interface ProductConsumptionService {
    List<ProductConsumptionDTO> getAllConsumptions();
    List<ProductConsumptionDTO> getProductConsumptions(String code);
    ProductConsumptionDTO getProductInOrder(ProductOrderDTO productOrderDTO);
    void deleteRecord(ProductOrderDTO productOrderDTO);
}
