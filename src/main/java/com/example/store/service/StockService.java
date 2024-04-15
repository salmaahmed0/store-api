package com.example.store.service;

import com.example.store.model.validation.ValidateProductRequestDTO;
import com.example.store.model.validation.ValidateProductResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;

import java.util.List;

public interface StockService {

    List<StockResponseDTO> findAll();
    List<StockResponseDTO> findAllByProductCodeContainingIgnoreCase(String productCode);

    StockResponseDTO save(StockRequestDTO stockRequestDTO);

    StockResponseDTO updateStock(StockResponseDTO stockResponseDTO);

    void deleteStock(long id);

    List<ValidateProductResponseDTO> validateProducts(List<ValidateProductRequestDTO> productDTOS);

    Boolean consumeProductsFromStocks(List<ValidateProductRequestDTO> productDTOS);

}
