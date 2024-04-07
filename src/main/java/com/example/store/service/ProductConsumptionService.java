package com.example.store.service;

import com.example.store.model.other.ProductConsumptionDTO;

import java.util.List;

public interface ProductConsumptionService {
    List<ProductConsumptionDTO> getAllConsumptions();
    List<ProductConsumptionDTO> getProductConsumptions(String code);
}
