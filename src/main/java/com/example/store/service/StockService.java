package com.example.store.service;

import com.example.store.model.validation.ProductValidationRequestDTO;
import com.example.store.model.validation.ProductValidateResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;

import java.util.List;

public interface StockService {

    List<StockResponseDTO> findAll();
    List<StockResponseDTO> findStocksByProductCode(String productCode);

    StockResponseDTO createNewStock(StockRequestDTO stockRequestDTO);

    StockResponseDTO updateStock(StockResponseDTO stockResponseDTO);

    void deleteStock(long id);

    List<ProductValidateResponseDTO> validateProducts(List<ProductValidationRequestDTO> productDTOS);

    String  checkProductsValidationAndConsume(List<ProductValidationRequestDTO> productDTOS);

}
