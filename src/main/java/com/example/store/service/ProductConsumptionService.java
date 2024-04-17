package com.example.store.service;

import com.example.store.model.productConsumption.ProductConsumptionDTO;
import com.example.store.model.productConsumption.ProductOrderDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductConsumptionService {
    List<ProductConsumptionDTO> getAllConsumptions();
    List<ProductConsumptionDTO> getProductConsumptions(String code);
    ProductConsumptionDTO getProductInOrder(String productCode, String orderCode);
    void deleteRecord(ProductOrderDTO productOrderDTO);
}
