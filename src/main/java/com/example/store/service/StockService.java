package com.example.store.service;

import com.example.store.model.other.ValidateProductRequestDTO;
import com.example.store.model.other.ValidateProductResponseDTO;
import com.example.store.model.stock.StockRequestDTO;
import com.example.store.model.stock.StockResponseDTO;

import java.util.List;

public interface StockService {

    List<StockResponseDTO> findAll();
    List<StockResponseDTO> findAllByProductCodeContainingIgnoreCase(String productCode);

    String save(StockRequestDTO stockRequestDTO);

    String updateStock(StockResponseDTO stockResponseDTO);

    String deleteStock(long id);

    List<ValidateProductResponseDTO> validateProducts(List<ValidateProductRequestDTO> productDTOS);

    Boolean consumeProductsFromStocks(List<ValidateProductRequestDTO> productDTOS);

}
